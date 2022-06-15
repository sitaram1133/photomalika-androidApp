package aum.apps.presentpoint.ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import aum.apps.presentpoint.R

class ViewserverDialog {
    fun showMessageDialog(activity: Activity?, message: String?) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.responsedisplaydilog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val textdata = dialog.findViewById<TextView>(R.id.apimessageonfailureid)
        val dialogBtn_remove = dialog.findViewById<TextView>(R.id.txtbtnyesid)
        textdata.text = message?.toString()
        dialogBtn_remove.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
