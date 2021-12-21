package lavsam.gb.profiaslesson5.historyscreen.presentation.viewModel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import lavsam.gb.profiaslesson5.core.presentation.viewModel.base.BaseViewModel
import lavsam.gb.profiaslesson5.historyscreen.domain.interactor.HistoryInteractor
import lavsam.gb.profiaslesson5.historyscreen.parseLocalSearchResults
import lavsam.gb.profiaslesson5.model.AppState

class HistoryActivityViewModel(
    private val historyInteractor: HistoryInteractor
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> = liveDataForViewToObserve

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(AppState.Loading(null))
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(
            parseLocalSearchResults(
                historyInteractor.getData(
                    word,
                    isOnline
                )
            )
        )
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.postValue(AppState.Success(null))
        super.onCleared()
    }
}