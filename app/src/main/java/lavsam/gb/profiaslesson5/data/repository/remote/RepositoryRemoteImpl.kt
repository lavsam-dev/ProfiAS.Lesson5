package lavsam.gb.profiaslesson5.data.repository.remote

import lavsam.gb.profiaslesson5.data.dataSource.remote.VocabularyDataSourceRemote
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

class RepositoryRemoteImpl(
    private val vocabularyDataSource: VocabularyDataSourceRemote<List<VocabularyDataModel>>
) : RepositoryRemote<List<VocabularyDataModel>> {

    override suspend fun getData(word: String): List<VocabularyDataModel> =
        vocabularyDataSource.getData(word)
}