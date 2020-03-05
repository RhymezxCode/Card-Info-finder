package rhymezx.code.card_information_finder.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_splash_screen.*
import rhymezx.code.card_information_finder.R


class SplashScreen : AppCompatActivity() {

    var back_pressed: Long? = 0

    private var handler: Handler? = null
    private var progressBarStatus: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Thread(Runnable {
            while (progressBarStatus < 100) {
                progressBarStatus++
                android.os.SystemClock.sleep(20)
                handler?.post { progressBar.progress = progressBarStatus }
            }

            if (progressBarStatus == 100) {
                val intent = Intent(this, CardOptionSelection::class.java)
                startActivity(intent)
            }
        }).start()
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
