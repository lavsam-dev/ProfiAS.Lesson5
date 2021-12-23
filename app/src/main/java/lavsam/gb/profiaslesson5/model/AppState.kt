package lavsam.gb.profiaslesson5.model

sealed class AppState {
    data class Success(val data: List<VocabularyDataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val loading: Int?) : AppState()
}