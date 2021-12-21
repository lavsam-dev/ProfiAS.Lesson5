package lavsam.gb.profiaslesson5.utils

import lavsam.gb.profiaslesson5.data.room.HistoryEntity
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.Meanings
import lavsam.gb.profiaslesson5.model.VocabularyDataModel

fun parseOnlineSearchResults(appState: AppState): AppState =
    AppState.Success(mapResult(appState, true))

fun parseLocalSearchResults(appState: AppState): AppState =
    AppState.Success(mapResult(appState, false))

private fun mapResult(
    appState: AppState,
    isOnline: Boolean
): List<VocabularyDataModel> {
    val newSearchResults = arrayListOf<VocabularyDataModel>()
    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, isOnline, newSearchResults)
        }
        else -> {}
    }
    return newSearchResults
}

private fun getSuccessResultData(
    appState: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<VocabularyDataModel>
) {
    val vocabularyDataModel: List<VocabularyDataModel> = appState.data as List<VocabularyDataModel>
    if (vocabularyDataModel.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in vocabularyDataModel) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in vocabularyDataModel) {
                newDataModels.add(VocabularyDataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(
    vocabularyDataModel: VocabularyDataModel,
    newDataModels: ArrayList<VocabularyDataModel>
) {
    if (!vocabularyDataModel.text.isNullOrBlank() && !vocabularyDataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in vocabularyDataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(VocabularyDataModel(vocabularyDataModel.text, newMeanings))
        }
    }
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<VocabularyDataModel> {
    val searchResult = ArrayList<VocabularyDataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(VocabularyDataModel(entity.word, null))
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
                HistoryEntity(searchResult[0].text!!, null)
            }
        }
        else -> null
    }
}


fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}