@file:Suppress("DEPRECATION")

package rhymezx.code.card_information_finder.providers

import android.content.Context
import android.net.ConnectivityManager

object CheckNetwork {
    fun isConnected(context: Context): Boolean {
        val conn = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val netInfo = conn.activeNetworkInfo

        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            val wifi = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            mobile != null && mobile.isConnectedOrConnecting || wifi != null && wifi.isConnectedOrConnecting
        } else {
            false
        }
    }
}