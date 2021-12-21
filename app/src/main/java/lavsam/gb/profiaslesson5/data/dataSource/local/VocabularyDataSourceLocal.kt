package lavsam.gb.profiaslesson5.data.dataSource.local

import lavsam.gb.profiaslesson5.data.dataSource.remote.VocabularyDataSourceRemote
import lavsam.gb.profiaslesson5.model.AppState

interface VocabularyDataSourceLocal<T> : VocabularyDataSourceRemote<T> {
    suspend fun saveToDB(appState: AppState)
}