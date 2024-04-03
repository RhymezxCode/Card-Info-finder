package rhymezx.code.card_information_finder.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.cardform.view.CardForm
import com.github.kittinunf.fuel.core.isClientError
import com.github.kittinunf.fuel.core.isServerError
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import com.google.android.material.snackbar.Snackbar
import rhymezx.code.card_information_finder.R
import rhymezx.code.card_information_finder.databinding.ActivityCardInformationDisplayBinding
import rhymezx.code.card_information_finder.databinding.ActivityCardOptionSelectionBinding
import rhymezx.code.card_information_finder.databinding.ActivityCardProcessorBinding
import rhymezx.code.card_information_finder.models.CardInfoPage
import rhymezx.code.card_information_finder.models.Urls
import rhymezx.code.card_information_finder.providers.CheckNetwork.isConnected


class CardProcessor : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCardProcessorBinding
    //on back press
    private var backPressed: Long? = 0

    //card form layout
    private var cardForm: CardForm? = null

    //Card information
    private var cardBrand: String? = null
    private var bankName: String? = null
    private var cardType: String? = null
    private var countryName: String? = null

    //bundle data
    private var bundle: Bundle = Bundle()

    private var dialog: LoadingDialog = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        binding = ActivityCardProcessorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardForm = binding.card

        cardForm?.cardRequired(true)?.setup(this)

        binding.back.setOnClickListener(this)

        binding.proceed.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.proceed -> {
                    if (isConnected(this)) {
                        if (cardForm!!.cardNumber.isNotEmpty()) {

                            getInformation(cardForm!!.cardNumber) { CardInfoPage ->
                                cardBrand = CardInfoPage.brand
                                cardType = CardInfoPage.type
                                bankName = CardInfoPage.bank!!.name
                                countryName = CardInfoPage.country!!.name
                            }
                        } else {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Please enter all your card number!!!!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "No Internet Connection!!!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }


                }

                R.id.back -> finish()


            }
        }
    }

    private fun getInformation(
        card_number: String,
        responseHandler: (result: CardInfoPage) -> Unit
    ) {
        dialog.loadingAlertDialog()

        Urls.getInformationUrl(card_number)
            .httpGet()
            .responseObject(CardInfoPage.Deserializer())
            { _, response, result ->

                Log.v("response: ", response.toString())

                try {
                    when {
                        response.isSuccessful -> {
                            val (data, _) = result
                            responseHandler.invoke(data as CardInfoPage)
                            bundle.putString("brand", cardBrand)
                            bundle.putString("type", cardType)
                            bundle.putString("bank_name", bankName)
                            bundle.putString("country_name", countryName)

                            dialog.dismissAlertDialog()

                            when (response.statusCode) {
                                200 -> {
                                    startActivity(
                                        Intent(
                                            this,
                                            CardInformationDisplay::class.java
                                        ).putExtras(bundle)
                                    )
                                    finish()
                                }
                            }
                        }
                        response.isServerError -> {
                            dialog.dismissAlertDialog()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Server error!!!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        response.isClientError -> {
                            dialog.dismissAlertDialog()
                            when (response.statusCode) {
                                404 -> {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Card information not found!",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                                429 -> {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Too many request!",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                                400 -> {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Bad request!",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                                else ->
                                {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Client error!!!",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }
                        else -> {
                            dialog.dismissAlertDialog()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Something went wrong!!!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }


                } catch (error: Exception) {
                    Log.v("error: ", error.toString())
                }

            }


    }

    override fun onBackPressed() {
        if (backPressed!! + 2000 > System.currentTimeMillis()) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
            finishAffinity()
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Touch again to exit",
                Snackbar.LENGTH_SHORT
            ).show()
            backPressed = System.currentTimeMillis()
        }
    }


}


