package lavsam.gb.profiaslesson5.core.domain.interactor

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}