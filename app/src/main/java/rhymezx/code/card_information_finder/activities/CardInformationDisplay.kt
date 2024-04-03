package rhymezx.code.card_information_finder.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import rhymezx.code.card_information_finder.R
import rhymezx.code.card_information_finder.databinding.ActivityCardInformationDisplayBinding
import rhymezx.code.card_information_finder.databinding.ActivitySplashScreenBinding

class CardInformationDisplay : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCardInformationDisplayBinding

    var back_pressed: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        binding = ActivityCardInformationDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            finish()
        }

        val bundle = intent.extras

        if (bundle != null) {
            binding.cardBrand.text = bundle.getString("brand")
            binding.cardType.text = bundle.getString("type")
            binding.bank.text = bundle.getString("bank_name")
            binding.country.text = bundle.getString("country_name")

            Snackbar.make(
                findViewById(android.R.id.content),
                "Done!!!",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.menu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.menu -> {
                    startActivity(
                        Intent(
                            this,
                            CardOptionSelection::class.java
                        )
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (back_pressed!! + 2000 > System.currentTimeMillis()) {
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
            back_pressed = System.currentTimeMillis()
        }
    }
}
