package rhymezx.code.card_information_finder.models

object Urls {
    val BaseUrl = "https://lookup.binlist.net/"

    fun getInformationUrl(card_number: String): String {
        return BaseUrl + card_number
    }

}