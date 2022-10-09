package com.moeslim


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import com.moeslim.constans.ActionType
import com.moeslim.helper.PreferencesHelper

class MainActivity : AppCompatActivity() {

    lateinit var sharedPref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPref = PreferencesHelper(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn_welcome)
            btn.setOnClickListener {
                val intent = Intent(this, Auth_Login::class.java)
                startActivity(intent)
            }
    }
    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(ActionType.PREF_IS_LOGIN)) {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }

}
