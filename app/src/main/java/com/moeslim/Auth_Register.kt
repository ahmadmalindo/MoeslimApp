package com.moeslim

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Auth_Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_register)

        // back to page login
        val btn = findViewById<LinearLayout>(R.id.btn_back_login)
        btn.setOnClickListener {
            val intent = Intent(this, Auth_Login::class.java)
            startActivity(intent)
        }
        // back to page login
        val btnConfirm = findViewById<Button>(R.id.btn_confrim_register)
        btnConfirm.setOnClickListener {
            validationSchema()
        }

        // username
        val username = findViewById<EditText>(R.id.input_username)
        val showHideUser = findViewById<LinearLayout>(R.id.show_hide_input_username)
        // email
        val email = findViewById<EditText>(R.id.input_email)
        val showHideEmail = findViewById<LinearLayout>(R.id.show_hide_input_email)
        // password
        val password = findViewById<EditText>(R.id.input_pwd)
        val showHidePass = findViewById<LinearLayout>(R.id.show_hide_input_pwd)
        // confirm password
        val confirmPwd = findViewById<EditText>(R.id.input_pwd_confirm)
        val showHideConfirmPwd = findViewById<LinearLayout>(R.id.show_hide_input_pwd_confirm)

        // act username
        username.setOnClickListener {
            username.setBackgroundResource(R.drawable.input_shape)
            username.setHint("")
            showHideUser.visibility = View.VISIBLE
            val username = username.text.toString()
            validationUsername(username)
        }
        // act email
        email.setOnClickListener {
            email.setBackgroundResource(R.drawable.input_shape)
            email.setHint("")
            showHideEmail.visibility = View.VISIBLE
            val email = email.text.toString()
            validationEmail(email)
        }
        // act password
        var showHidePw = true
        password.setOnClickListener {
            password.setBackgroundResource(R.drawable.input_shape)
            password.setHint("")
            showHidePass.visibility = View.VISIBLE
            showHidePw =!showHidePw
            showHidePw(showHidePw, password)
            val password = password.text.toString()
            validationPassword(password)
        }
        // act email
        var showHideCfrPw = true
        confirmPwd.setOnClickListener {
            confirmPwd.setBackgroundResource(R.drawable.input_shape)
            confirmPwd.setHint("")
            showHideConfirmPwd.visibility = View.VISIBLE
            showHideCfrPw = !showHideCfrPw
            showHideCfrPw(showHideCfrPw, confirmPwd)
            val confirmPwd = confirmPwd.text.toString()
            val password = password.text.toString()
            validationConfrimPassword(confirmPwd, password)
        }
    }

    private fun showHidePw(show : Boolean, pass : EditText) {

        if (show) {
            pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eyeopenicon, 0)
        }
        else {
            pass.transformationMethod = PasswordTransformationMethod.getInstance()
            pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eyeclosedicon, 0)
        }
    }

    private fun showHideCfrPw(show : Boolean, pass : EditText) {

        if (show) {
            pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eyeopenicon, 0)
        }
        else {
            pass.transformationMethod = PasswordTransformationMethod.getInstance()
            pass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eyeclosedicon, 0)
        }
    }

    private fun validationSchema() {

        val user = findViewById<EditText>(R.id.input_username).text.toString()
        val email = findViewById<EditText>(R.id.input_email).text.toString()
        val pass = findViewById<EditText>(R.id.input_pwd).text.toString()
        val cfrPass = findViewById<EditText>(R.id.input_pwd_confirm).text.toString()

        if (!validationUsername(username = user) || !validationEmail(email = email) || !validationPassword(pass = pass) || !validationConfrimPassword(pass = pass, cfrPass = cfrPass ) ) {
            return;
        }
        else {
            val auth = Firebase.auth
            auth.createUserWithEmailAndPassword(email, cfrPass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profile = FirebaseAuth.getInstance().currentUser
                    val user = UserProfileChangeRequest.Builder().setDisplayName(user).build()
                    Toast.makeText(this, "indo", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                        val intent = Intent(this, Auth_Login::class.java)
                        startActivity(intent)
                    }, 1000)
                    if (profile != null) {
                        profile.updateProfile(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "OKOK")
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validationUsername(username : String): Boolean {

        val USERNAME = Pattern.compile(
            "^(?=[a-zA-Z0-9._]{3,50}\$)(?!.*[_.]{2})[^_.].*[^_.]\$"
        )
        fun isValidatingUser(str : String): Boolean {
            return USERNAME.matcher(str).matches()
        }

        if (username == "") {
            Toast.makeText(this, "Email user", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (!isValidatingUser(username)) {
            Toast.makeText(this, "Email VAlidasi", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private  fun validationEmail(email : String): Boolean {

        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        fun isValidString(str: String): Boolean{
            return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
        }

        if (email == "") {
            Toast.makeText(this, "Email Diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(!isValidString(email)) {
            Toast.makeText(this, "Email VAlidasi", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validationPassword(pass : String): Boolean {

        val PASSWORD = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}\$"
        )

        fun isValidating(str : String): Boolean {
            return PASSWORD.matcher(str).matches()
        }

        if (pass == "") {
            return false
        }
        else if (!isValidating(pass)) {
            Toast.makeText(this, "Password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validationConfrimPassword(cfrPass : String, pass : String): Boolean {

        val PASSWORD = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}\$"

        )

        fun isValidating(str : String): Boolean {
            return PASSWORD.matcher(str).matches()
        }

        if (cfrPass == "") {
            return false
        }
        else if (!isValidating(cfrPass)) {
            Toast.makeText(this, "Password", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (!cfrPass.equals(pass)) {
            Toast.makeText(this, "Password ra macth", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}