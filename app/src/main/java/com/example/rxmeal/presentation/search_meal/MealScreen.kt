package com.example.rxmeal.presentation.search_meal

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rxmeal.presentation.components.SearchField
import com.example.rxmeal.presentation.search_meal.components.MealCard
import com.example.rxmeal.presentation.search_meal.viewModel.MealViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MealScreenRoot(
    viewModel: MealViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MealEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MainMealScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MainMealScreen(
    state: MealState,
    onAction: (MealAction) -> Unit
) {
    Scaffold(
        topBar = {
            SearchField(
                value = state.searchQuery,
                onValueChange = { onAction(MealAction.OnSearchQueryChange(it)) }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            state.errorMsg != null -> Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.errorMsg)
            }

            else -> {
                LazyColumn (
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.meals) { meal ->
                        MealCard(
                            meal = meal,
                            onUpsert = { onAction(MealAction.OnUpsertClick(meal)) },
                            onDelete = { onAction(MealAction.OnDeleteClick(meal)) }
                        )
                    }
                }
            }
        }
    }
}
