package rhymezx.code.card_information_finder.util

import android.app.Activity
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnack() {
    Snackbar.make(
        this.findViewById(android.R.id.content),
        "Please enter the first 8 digits of your card!",
        Snackbar.LENGTH_SHORT
    ).show()
}