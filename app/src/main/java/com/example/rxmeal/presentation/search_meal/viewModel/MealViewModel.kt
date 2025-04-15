package com.example.rxmeal.presentation.search_meal.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.use_case.DeleteMealFromLocalUseCase
import com.example.rxmeal.domain.use_case.GetAllFromLocalUseCase
import com.example.rxmeal.domain.use_case.SearchUseCase
import com.example.rxmeal.domain.use_case.UpsertMealLocalUseCase
import com.example.rxmeal.presentation.search_meal.MealAction
import com.example.rxmeal.presentation.search_meal.MealEffect
import com.example.rxmeal.presentation.search_meal.MealState
import com.example.rxmeal.utils.RxErrorHandler
import com.example.rxmeal.utils.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val searchUseCase: SearchUseCase,
    private val deleteMealFromLocalUseCase: DeleteMealFromLocalUseCase,
    private val upsertMealLocalUseCase: UpsertMealLocalUseCase,
    private val getAllFromLocalUseCase: GetAllFromLocalUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(MealState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MealEffect>()
    val effect = _effect.asSharedFlow()

    private val actionFlow = MutableSharedFlow<MealAction>()
    private val compositeDisposable = CompositeDisposable()

    init {
        handleActions()
        loadFavorites()
    }

    fun onAction(action: MealAction) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    private fun handleActions() {
        actionFlow
            .onEach { action ->
                when (action) {
                    is MealAction.OnSearchQueryChange -> searchMeal(action.query)
                    is MealAction.OnUpsertClick -> upsert(action.meal)
                    is MealAction.OnDeleteClick -> delete(action.meal)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadFavorites() {
        getAllFromLocalUseCase.execute()
            .subscribe(
                { favs -> _state.update { it.copy(favorites = favs) } },
                { error -> _state.update { it.copy(errorMsg = RxErrorHandler.handle(error)) } }
            ).addTo(compositeDisposable)
    }



    private fun searchMeal(query: String) {
        _state.update { it.copy(searchQuery = query) }

                searchUseCase.execute(query)
                    .doOnSubscribe {
                        _state.update { it.copy(isLoading = true, errorMsg = null) }
                    }
            .subscribe(
                { meals ->
                    _state.update { it.copy(meals = meals, isLoading = false) }
                },
                { error ->
                    val msg = RxErrorHandler.handle(error)
                    _state.update {it.copy(errorMsg = msg, isLoading = false) }
                    viewModelScope.launch { _effect.emit(MealEffect.ShowToast(msg)) }
                }
            )
    }

//    fun updateQuery(query: String) {
//        _query.onNext(query)
//    }

    private fun upsert(meal: Meal) {
        upsertMealLocalUseCase.execute(meal)
            .subscribe({},{}).addTo(compositeDisposable)
    }

    private fun delete(meal: Meal) {
        deleteMealFromLocalUseCase.execute(meal)
            .subscribe({},{}).addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
