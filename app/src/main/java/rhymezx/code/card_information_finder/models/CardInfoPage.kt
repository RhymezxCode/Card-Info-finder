package rhymezx.code.card_information_finder.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class CardInfoPage {
    var brand: String? = null
    var type: String? = null
    var bank: CardBankInfo? = null
    var country: CardCountryInfo? = null

    class Deserializer : ResponseDeserializable<CardInfoPage> {
        override fun deserialize(content: String) =
            Gson().fromJson(content, CardInfoPage::class.java)
    }

}

