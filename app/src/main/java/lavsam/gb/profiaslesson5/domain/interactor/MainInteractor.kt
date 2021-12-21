package lavsam.gb.profiaslesson5.domain.interactor

import lavsam.gb.profiaslesson5.core.domain.interactor.Interactor
import lavsam.gb.profiaslesson5.data.repository.local.RepositoryLocal
import lavsam.gb.profiaslesson5.data.repository.remote.RepositoryRemote
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

class MainInteractor(
    private val repositoryRemote: RepositoryRemote<List<VocabularyDataModel>>,
    private val repositoryLocal: RepositoryLocal<List<VocabularyDataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}