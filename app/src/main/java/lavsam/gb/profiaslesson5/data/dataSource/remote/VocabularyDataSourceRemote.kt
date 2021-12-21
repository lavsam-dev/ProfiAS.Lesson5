package lavsam.gb.profiaslesson5.data.dataSource.remote

interface VocabularyDataSourceRemote<T> {
    suspend fun getData(word: String): T
}