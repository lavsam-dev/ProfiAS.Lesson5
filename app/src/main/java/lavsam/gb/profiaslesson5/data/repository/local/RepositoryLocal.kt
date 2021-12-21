package lavsam.gb.profiaslesson5.data.repository.local

import lavsam.gb.profiaslesson5.data.repository.remote.RepositoryRemote
import lavsam.gb.profiaslesson5.model.AppState

interface RepositoryLocal<T> : RepositoryRemote<T> {
    suspend fun saveToDB(appState: AppState)
}