package com.example.rxmeal.presentation.search_meal.viewModel

import androidx.lifecycle.ViewModel
import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.use_case.DeleteMealFromLocalUseCase
import com.example.rxmeal.domain.use_case.GetAllFromLocalUseCase
import com.example.rxmeal.domain.use_case.SearchUseCase
import com.example.rxmeal.domain.use_case.UpsertMealLocalUseCase
import com.example.rxmeal.utils.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val searchUseCase: SearchUseCase,
    private val deleteMealFromLocalUseCase: DeleteMealFromLocalUseCase,
    private val upsertMealLocalUseCase: UpsertMealLocalUseCase,
    private val getAllFromLocalUseCase: GetAllFromLocalUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private val _query = PublishSubject.create<String>()

    private val compositeDisposable = CompositeDisposable()

    init {
        searchMeal()
        loadFavorite()
    }

    private fun loadFavorite() {
        getAllFromLocalUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    _state.update {
                        it.copy(
                            favorite = list
                        )
                    }
                },
                { error ->
                    _state.update {
                        it.copy(
                            error = error.localizedMessage.toString()
                        )
                    }
                }
            ).addTo(compositeDisposable)
    }

    private fun searchMeal() {
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
                    _state.update { UiState(results = results) }
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

    fun upsert(meal: Meal) {
        upsertMealLocalUseCase.execute(meal)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun delete(meal: Meal) {
        deleteMealFromLocalUseCase.execute(meal)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val results: List<Meal>? = null,
    val favorite: List<Meal>? = null
)