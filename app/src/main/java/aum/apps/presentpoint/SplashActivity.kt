package aum.apps.presentpoint


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Space
import android.widget.Toast
import androidx.appcompat.app.ActionBar



class Splash : AppCompatActivity() {
    private val sharedPrefFile = "userAuthentication"
    private val imViewAndroid: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.hide()



        Handler().postDelayed({
            if (isNetworkAvailable(this)){
                if (serverStatus("url")){
                    val sharedPreferences: SharedPreferences = this.getSharedPreferences(
                        sharedPrefFile,
                        Context.MODE_PRIVATE
                    )

                    val authstatus = sharedPreferences.getBoolean("isAuth",false)
                    val userId = sharedPreferences.getString("user_id","nc")
                    val email = sharedPreferences.getString("email","nc")

                    if (authstatus  && userId!="nc"){
                        startActivity(Intent(this@Splash,MainActivity::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this,MainActivity::class.java)) //login Activity
                        finish()
                    }
                }
                else{
                    Toast.makeText(this,"Server not Available",Toast.LENGTH_SHORT).show()
                }
            }else{
                startActivity(Intent(this@Splash,NointernetActivity::class.java))
                finish()
            }
        },2500)

    }

    private fun serverStatus(url: Any): Boolean {
        return true
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

}
