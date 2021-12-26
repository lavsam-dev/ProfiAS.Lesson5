package lavsam.gb.profiaslesson5.data

import lavsam.gb.profiaslesson5.data.room.HistoryEntity
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<VocabularyDataModel> {
    val searchResult = ArrayList<VocabularyDataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(VocabularyDataModel(entity.word, listOf()))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text, "")
            }
        }
        else -> null
    }
}