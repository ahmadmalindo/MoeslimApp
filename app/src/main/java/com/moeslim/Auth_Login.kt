package com.moeslim

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.moeslim.constans.ActionType
import com.moeslim.constans.ApiQuran
import com.moeslim.helper.PreferencesHelper
import com.moeslim.model.quran.ResponseApiDetailDataQuran
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Auth_Login : AppCompatActivity() {

    lateinit var sharedPref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)

        sharedPref = PreferencesHelper(this)
        val id = intent.getStringExtra(ActionType.PREF_DETAIL_SURRAH)

        //btn register
        val btn = findViewById<TextView>(R.id.btn_register)
        btn.setOnClickListener {
            val intent = Intent(this, Auth_Register::class.java)
            startActivity(intent)
        }
        //btn forgot password
        val btn2 = findViewById<TextView>(R.id.btn_forgotPassword)
        btn2.setOnClickListener( View.OnClickListener() {
            showDialog()
        })
        // btn login
        val btn3 = findViewById<Button>(R.id.btn_handleLogin)
        btn3.setOnClickListener {
            handleSignIn()
        }

        // email
        val email = findViewById(R.id.input_email) as EditText
        val show = findViewById(R.id.show_hide_input) as LinearLayout
        // password
        val password = findViewById(R.id.input_pwd) as EditText
        val showPwd = findViewById(R.id.show_hide_input_pwd) as LinearLayout
        // act email
        email.setOnClickListener{
            email.setBackgroundResource(R.drawable.input_shape)
            email.setHint("")
            show.visibility = View.VISIBLE
            val email = email.text.toString()
            validateEmail(email)
        }
        // act password
        var showPassword = true
        password.setOnClickListener {
            password.setBackgroundResource(R.drawable.input_shape)
            password.setHint("")
            showPwd.visibility = View.VISIBLE
            showPassword = !showPassword
            showHide(!showPassword, password)
            val pass = password.text.toString()
            validatePassword(pass)
        }
    }

    private fun handleSignIn() {

        val email = findViewById<EditText>(R.id.input_email).text.toString()
        val password = findViewById<EditText>(R.id.input_pwd).text.toString()

        if (!validateEmail(email) || !validatePassword(password)) {
            return
        }
        else {
            val load = findViewById<ProgressBar>(R.id.activity_indicator)
            load.visibility = View.VISIBLE
            val auth = Firebase.auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPref.put(ActionType.PREF_IS_LOGIN, true)
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "succes", Toast.LENGTH_SHORT).show()
                    load.visibility = View.GONE
                }
                else {
                    Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show()
                    load.visibility = View.GONE
                }
            }
        }
    }

    private fun validateEmail(email : String): Boolean {

        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        fun isValidating(str : String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
        }

        if (email == "") {
            return false
        }
        else if (!isValidating(email)) {
            Toast.makeText(this, "email", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validatePassword(pass : String) : Boolean {

        val PASSWORD = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}\$"
        )
        fun isValidate(str : String): Boolean {
            return PASSWORD.matcher(str).matches()
        }

        if (pass == "") {

        }
        else if (!isValidate(pass)) {
            Toast.makeText(this, "password", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun showDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_show_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        val btn = dialog.findViewById<Button>(R.id.btn_handleforgotpassword)
        btn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private  fun showHide(show : Boolean, pass : EditText) {

        if (show) {
            pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eyeopenicon, 0)
        }
        else {
            pass.transformationMethod = PasswordTransformationMethod.getInstance()
            pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eyeclosedicon, 0)
        }
    }

}