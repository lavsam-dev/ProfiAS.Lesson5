package lavsam.gb.profiaslesson5.model

import com.google.gson.annotations.SerializedName

private const val GSON_KEY_TEXT = "text"
private const val GSON_KEY_MEANINGS = "meanings"
private const val GSON_KEY_TRANSLATION = "translation"
private const val GSON_KEY_IMAGE_URL = "imageUrl"
private const val GSON_KEY_TRANSLATION_TEXT = "text"

class VocabularyDataModel(
    @SerializedName(GSON_KEY_TEXT) val text: String?,
    @SerializedName(GSON_KEY_MEANINGS) val meanings: List<Meanings>?
)

class Meanings(
    @SerializedName(GSON_KEY_TRANSLATION) val translation: Translation?,
    @SerializedName(GSON_KEY_IMAGE_URL) val imageUrl: String?
)

class Translation(@SerializedName(GSON_KEY_TRANSLATION_TEXT) val translation: String?)