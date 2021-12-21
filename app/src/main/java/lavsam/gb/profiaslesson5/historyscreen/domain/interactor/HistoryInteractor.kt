package lavsam.gb.profiaslesson5.historyscreen.domain.interactor

import lavsam.gb.profiaslesson5.core.domain.interactor.Interactor
import lavsam.gb.profiaslesson5.data.repository.local.RepositoryLocal
import lavsam.gb.profiaslesson5.data.repository.remote.RepositoryRemote
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

class HistoryInteractor(
    private val repositoryRemote: RepositoryRemote<List<VocabularyDataModel>>,
    private val repositoryLocal: RepositoryLocal<List<VocabularyDataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}