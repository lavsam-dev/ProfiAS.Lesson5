package lavsam.gb.profiaslesson5.data.dataSource.local

import lavsam.gb.profiaslesson5.data.convertDataModelSuccessToEntity
import lavsam.gb.profiaslesson5.data.mapHistoryEntityToSearchResult
import lavsam.gb.profiaslesson5.data.room.HistoryDao
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

class VocabularyDataSourceLocalImpl(
    private val historyDao: HistoryDao
) : VocabularyDataSourceLocal<List<VocabularyDataModel>> {

    override suspend fun getData(word: String): List<VocabularyDataModel> =
        mapHistoryEntityToSearchResult(historyDao.all())

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}