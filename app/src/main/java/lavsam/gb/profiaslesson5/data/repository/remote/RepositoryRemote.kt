package lavsam.gb.profiaslesson5.data.repository.remote

interface RepositoryRemote<T> {
    suspend fun getData(word: String): T
}