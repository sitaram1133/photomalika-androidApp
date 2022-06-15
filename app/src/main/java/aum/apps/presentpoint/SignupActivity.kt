package aum.apps.presentpoint

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import aum.apps.presentpoint.LoginActivity
import aum.apps.presentpoint.MainActivity
import aum.apps.presentpoint.R
import aum.apps.presentpoint.services.ServiceBuilder
import aum.apps.presentpoint.ui.ViewserverDialog
import aum.apps.telecallers.Model.User
import aum.apps.presentpoint.services.UserAuthInterface
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private val sharedPrefFile = "userAuthentication"

    private var etfirstname : EditText? = null
    private var etlastname : EditText? = null
    private var etemail : EditText? = null
    private var etpassword : EditText? = null

    private var firstname: String? = null
    private var lastname: String? = null
    private var email:String? = null
    private var password: String? = null
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        etfirstname = findViewById(R.id.firstnameid)
        etlastname = findViewById(R.id.lastnameid)
        etemail = findViewById(R.id.etsignupemailid)
        etpassword = findViewById(R.id.etpasswordid)

        loginnowlinkid.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        etfirstname?.doOnTextChanged { text, start, before, count ->
            if (firstnamelayout1.isErrorEnabled){
                firstnamelayout1.setErrorEnabled(false)
            }
        }

        etlastname?.doOnTextChanged { text, start, before, count ->
            if (lastnamelayout1.isErrorEnabled){
                lastnamelayout1.setErrorEnabled(false)
            }
        }

        etemail?.doOnTextChanged { text, start, before, count ->
            if (emaillayout1.isErrorEnabled){
                emaillayout1.setErrorEnabled(false)
            }
        }

        etpassword?.doOnTextChanged { text, start, before, count ->
            if (passwordlayout1.isErrorEnabled){
                passwordlayout1.setErrorEnabled(false)
            }
        }

        signupokbtnid.setOnClickListener {

            firstname = etfirstname!!.text.toString()
            lastname = etlastname!!.text.toString()
            email = etemail!!.text.toString()
            password = etpassword!!.text.toString()


            if (firstname ==""){
                firstnamelayout1.error = "Please insert firstname"
            }
            if (lastname ==""){
                lastnamelayout1.error = "Please insert lastname"
            }
            if (email==""){
                emaillayout1.error = "Please insert an Email"
            }
            if (password==""){
                passwordlayout1.error = "Please insert Password"
            }
            else{
                if (firstname !="" && lastname !="" && email != "" && password !=""){
                    //if all conditions ok then go to next
                    if (email!!.matches(emailPattern.toRegex())){
                        if (isValidPassword(password)){
                            SignupUserNow(firstname!!, lastname!!, email!!, password!!)
                        }else{
                            passwordlayout1.error = "Password must contain caps  A-Z for first letter /n and a Symbol(@$#) and 1-9 numbers"
                        }
                    }else{
                        emaillayout1.error = "insert valid Email Address sam@ymail.com"
                    }
                }else{
                    Log.d("Data:::","Please insert all Details")
                }
            }
        }
    }

    private fun SignupUserNow(
        firstname: String,
        lastname: String,
        email: String,
        password: String
    ) {
        val checkoutService = ServiceBuilder.buildService(UserAuthInterface::class.java)
        val thisUser = User()
        thisUser.firstname = firstname
        thisUser.lastname = lastname
        thisUser.email = email
        thisUser.password = password
        thisUser.password2 = password
        val requestCall = checkoutService.SignupUser(thisUser)
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                Log.e(TAG, "NET_ERROR:" + response.toString());
                if (response.isSuccessful) {
                    var submissionResponse = response.body() // Use it or ignore it
                    Log.d("Data::",submissionResponse.toString())
                    if (submissionResponse!!.succes!!){
                        val sharedPreferences: SharedPreferences = getSharedPreferences(
                            sharedPrefFile,
                            Context.MODE_PRIVATE
                        )
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("isAuth",submissionResponse!!.isAuth)
                        editor.putString("user_id",submissionResponse!!.id)
                        editor.apply()
                        editor.commit()
                        if (submissionResponse!!.id !=""){
                            startActivity(Intent(this@SignupActivity,MainActivity::class.java))
                        }
                    }else{
                        if (submissionResponse.message != ""){
                            val samdialog = ViewserverDialog().showMessageDialog(this@SignupActivity,submissionResponse.message)
                        }
                    }
                } else {
                    val samdialog = ViewserverDialog().showMessageDialog(this@SignupActivity,"Response not found 500")
                    alertmessageatAuthid.text = "response not found 500"
                }
            }



            override fun onFailure(call: Call<User>, t: Throwable) {
                val samdialog = ViewserverDialog().showMessageDialog(this@SignupActivity,"404 failure")
                Toast.makeText(
                    this@SignupActivity,
                    "cannot Uploaded info 404", Toast.LENGTH_LONG
                ).show()
                //startActivity(Intent(this@LoginActivity,NoserverActivity::class.java))
                //finish()
            }
        })
    }

    private fun isValidPassword(password: String?) : Boolean {

        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)
            return passwordMatcher.find(password) != null
        } ?: return false
    }
}