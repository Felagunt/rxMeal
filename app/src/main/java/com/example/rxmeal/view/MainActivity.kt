package com.example.rxmeal.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.example.rxmeal.view.ui.theme.RxMealTheme
import com.example.rxmeal.viewModel.MealViewModel
import com.example.rxmeal.viewModel.UiState

class MainActivity : ComponentActivity() {

    val viewModel: MealViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RxMealTheme {

                val state by viewModel.state.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var query by rememberSaveable { mutableStateOf("") }
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        value = query,
                        onValueChange = {
                            query = it
                            viewModel.updateQuery(it)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: UiState,
    value: String,
    onValueChange: (String) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Search..."
                    )
                }
            )
        }
    ) { paddingValues ->

            if (state.isLoading) {
                Box(Modifier.padding(paddingValues).fillMaxSize())
                {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center)
                    )
                }
            }
            if(state.error.isNotEmpty()) {
                Box(Modifier.padding(paddingValues).fillMaxSize()) {
                    Text(
                        text = state.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
            state.results?.let { list ->
                if(list.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(list) { meal ->
                            Card(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(

                                ) {
                                    SubcomposeAsyncImage(
                                        model = meal.strMealThumb,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(320.dp),
                                        loading = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        },
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = meal.strMeal,
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp,vertical = 12.dp)
                                            .fillMaxWidth()
                                    )
                                    Text(
                                        text = meal.strInstructions,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }



}