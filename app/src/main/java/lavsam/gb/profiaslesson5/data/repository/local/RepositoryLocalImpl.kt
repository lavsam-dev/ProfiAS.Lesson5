package lavsam.gb.profiaslesson5.data.repository.local

import lavsam.gb.profiaslesson5.data.dataSource.local.VocabularyDataSourceLocal
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

class RepositoryLocalImpl(
    private val vocabularyDataSource: VocabularyDataSourceLocal<List<VocabularyDataModel>>
) : RepositoryLocal<List<VocabularyDataModel>> {

    override suspend fun getData(word: String): List<VocabularyDataModel> =
        vocabularyDataSource.getData(word)

    override suspend fun saveToDB(appState: AppState) {
        vocabularyDataSource.saveToDB(appState)
    }
}