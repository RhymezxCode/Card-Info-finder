package rhymezx.code.card_information_finder.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_card_information_display.*
import rhymezx.code.card_information_finder.R

class CardInformationDisplay : AppCompatActivity(), View.OnClickListener {

    var back_pressed: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setContentView(R.layout.activity_card_information_display)


        val bundle = intent.extras

        if (bundle != null) {
            card_brand.text = bundle.getString("brand")
            card_type.text = bundle.getString("type")
            bank.text = bundle.getString("bank_name")
            country.text = bundle.getString("country_name")

            Snackbar.make(
                findViewById(android.R.id.content),
                "Done!!!",
                Snackbar.LENGTH_SHORT
            ).show()
        }





        menu.setOnClickListener(this)
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
