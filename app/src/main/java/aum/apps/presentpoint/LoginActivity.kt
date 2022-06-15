package aum.apps.presentpoint

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import aum.apps.presentpoint.services.ServiceBuilder
import aum.apps.presentpoint.ui.ViewserverDialog
import aum.apps.telecallers.Model.User
import aum.apps.presentpoint.services.UserAuthInterface
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var emailidtxt: EditText? = null
    private var passwirdtxt: EditText? = null
    private val sharedPrefFile = "userAuthentication"
    private var email:String? = null
    private var password: String? = null
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //ProgressUIViewer(this).ShowProgressbarUI(this,true)
        loginactivitybtnid.setOnClickListener { startActivity(Intent(this@LoginActivity,SignupActivity::class.java))}
        emailidtxt = findViewById(R.id.emailidtextid)
        passwirdtxt = findViewById(R.id.passwordtxtid)

        emailidtxt?.doOnTextChanged { text, start, before, count ->
            if (emaillayoutid.isErrorEnabled){
                emaillayoutid.setErrorEnabled(false)
            }
        }
        passwirdtxt?.doOnTextChanged { text, start, before, count ->
            if (passwordlayoutid.isErrorEnabled){
                passwordlayoutid.setErrorEnabled(false)
            }
        }




        usrloginbtnid.setOnClickListener { view->
            progressBar1.visibility = View.VISIBLE
            email = emailidtxt!!.text.toString()
            password = passwirdtxt!!.text.toString()

            if (email == "" && password == ""){
                emaillayoutid.error = "Enter Email *"
                passwordlayoutid.error = "Enter Password *"
                Snackbar.make(view, "Please fill Username password", Snackbar.LENGTH_LONG)
                    .setAction("Action") {
                        // Responds to click on the action
                    }
                    .show()
            }else{

                if (email!!.matches(emailPattern.toRegex())) {
                    if (isValidPassword(password)){
                        loginUserNow(email!!,password!!,this)
                    }else{
                        passwordlayoutid.error = getString(R.string.passwordvalidationerror)
                        Snackbar.make(view, "Enter valid password", Snackbar.LENGTH_LONG)
                            .setAction("Action") {
                                // Responds to click on the action
                            }
                            .show()
                    }
                }else{
                    emaillayoutid.error = getString(R.string.emailerror)
                    Snackbar.make(view, "Enter valid Username / email", Snackbar.LENGTH_LONG)
                        .setAction("Action") {
                            // Responds to click on the action
                        }
                        .show()
                }

            }
        }

    }

    private fun loginUserNow(email: String, password: String, view: LoginActivity) {
        //ProgressUIViewer(this).ShowProgressbarUI(this,true)
        val checkoutService = ServiceBuilder.buildService(UserAuthInterface::class.java)
        val thisUser = User()
        thisUser.email = email
        thisUser.password = password
        val requestCall = checkoutService.LoginUser(thisUser)
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                //ProgressUIViewer(view).ShowProgressbarUI(view,false)
                progressBar1.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    var submissionResponse = response.body() // Use it or ignore it
                    Log.d("Data::",submissionResponse.toString())
                    if (submissionResponse!!.isAuth){
                        val sharedPreferences: SharedPreferences = getSharedPreferences(
                            sharedPrefFile,
                            Context.MODE_PRIVATE
                        )
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("isAuth",submissionResponse!!.isAuth)
                        editor.putString("user_id",submissionResponse!!.id)
                        editor.apply()
                        editor.commit()
                        onLoginComplete()
                    }else{
                        if (submissionResponse.message != ""){
                            ViewserverDialog().showMessageDialog(this@LoginActivity,submissionResponse.message)
                        }
                    }
                } else {

                    val samdialog = ViewserverDialog().showMessageDialog(this@LoginActivity,"Response not found")
                    alertmessageatAuthid.text = "response not found 500"
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                //ProgressUIViewer(view).ShowProgressbarUI(view,false)
                progressBar1.visibility = View.INVISIBLE
                val samdialog = ViewserverDialog().showMessageDialog(this@LoginActivity,"Response not found")
                Toast.makeText(
                    this@LoginActivity,
                    "cannot Uploaded info 404", Toast.LENGTH_LONG
                ).show()
                //startActivity(Intent(this@LoginActivity,NoserverActivity::class.java))
                //finish()
            }
        })
    }

    private fun onLoginComplete() {
        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
        finish()
    }

    private fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)
            return passwordMatcher.find(password) != null
        } ?: return false
    }

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (emaillayoutid.isErrorEnabled()) {
            emaillayoutid.setErrorEnabled(false)
        }
    }

}