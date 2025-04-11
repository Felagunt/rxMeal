package com.example.rxmeal.presentation.search_meal.viewModel

import androidx.lifecycle.ViewModel
import com.example.rxmeal.domain.use_case.SearchUseCase
import com.example.rxmeal.data.dto.MealDto
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private val _query = PublishSubject.create<String>()

    private val compositeDisposable = CompositeDisposable()

    init {
        val dispose = _query
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { query ->
                _state.update { UiState(isLoading = true) }
                searchUseCase.execute(query)
            }
            .subscribe(
                { results ->
                    _state.update { UiState(results = results.meals) }
                },
                { error ->
                    _state.update { UiState(error = error?.localizedMessage.toString()) }
                }
            )

        compositeDisposable.add(dispose)
    }

    fun updateQuery(query: String) {
        _query.onNext(query)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val results: List<MealDto>? = null
)