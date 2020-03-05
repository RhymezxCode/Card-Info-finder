package rhymezx.code.card_information_finder.activities

import android.app.Activity
import android.app.AlertDialog
import rhymezx.code.card_information_finder.R

data class LoadingDialog(var myActivity: Activity) {

    private var alertDialog: AlertDialog?= null
    private var activity = myActivity

    fun loadingAlertDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loader, null))
        builder.setCancelable(false)

        alertDialog = builder.create()
        alertDialog!!.show()

    }

    fun dismissAlertDialog(){
        alertDialog!!.dismiss()
    }


}