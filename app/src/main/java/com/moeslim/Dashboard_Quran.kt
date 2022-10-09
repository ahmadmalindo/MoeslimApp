package com.moeslim

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.moeslim.constans.ActionType
import com.moeslim.constans.ApiQuran
import com.moeslim.helper.PreferencesHelper
import com.moeslim.model.quran.ResponseApiDataAllQuran
import com.moeslim.utils.getApiQuran
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard_Quran : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val toogle = inflater.inflate(R.layout.fragment_dashboard__quran, container, false)
        // open navigation drawer
        val drawerLayout: DrawerLayout = toogle.findViewById(R.id.drawerLayout)
        val btn = toogle.findViewById<LinearLayout>(R.id.btn_open_drawer)
        btn.setOnClickListener {

            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        //firebase getUser
        val user = Firebase.auth.currentUser
        val name = toogle.findViewById<TextView>(R.id.text_username)
        user?.let {
            name.setText(user.displayName).toString()
            val email = user.email
            val emailVerif : Boolean = user.isEmailVerified
        }

        // RecyclerView
        val morty = toogle.findViewById<RecyclerView>(R.id.quran_recycler)
        val load = toogle.findViewById<ProgressBar>(R.id.activity_indicator)
        ApiQuran.getServiceDataAllQuran().getApiQuran().enqueue(object : Callback<ResponseApiDataAllQuran>{
            override fun onResponse(call: Call<ResponseApiDataAllQuran>, response: Response<ResponseApiDataAllQuran>) {
                if (response.isSuccessful) {
                    val responMorty = response.body()
                    val dataMorty = responMorty?.data
                    val mortyAdapter = dataMorty?.let { CostumAdapter(it) }
                    morty.apply {
                        layoutManager = LinearLayoutManager(this@Dashboard_Quran.requireActivity())
                        setHasFixedSize(true)
                        mortyAdapter?.notifyDataSetChanged()
                        adapter = mortyAdapter
                    }
                    load.visibility = View.GONE
                }
            }
            override fun onFailure(call: Call<ResponseApiDataAllQuran>, t: Throwable) {
                Toast.makeText(this@Dashboard_Quran.requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
        })

        // recent reading quran
        var sharePreff = PreferencesHelper(this@Dashboard_Quran.requireActivity())
        val surrah = toogle.findViewById<TextView>(R.id.text_surat_quran_recent)
        val ayyah = toogle.findViewById<TextView>(R.id.text_recent_ayyah)
        val nameSurrahRecent = sharePreff.getString(ActionType.PREF_RECENT_SURRAH)
        val ayyahSurrahRecent = sharePreff.getString(ActionType.PREF_RECENT_AYYAH)

        if (nameSurrahRecent !== null) {
            surrah.setText("${nameSurrahRecent}").toString()
        }
        else {
            surrah.setText("Nama Surat").toString()
        }

        if (ayyahSurrahRecent !== null) {
            ayyah.setText("${ayyahSurrahRecent}")
        }
        else {
            ayyah.setText("Quran")
        }

        //open more surrat quran
        val btnMore = toogle.findViewById<LinearLayout>(R.id.btn_more)
        btnMore.setOnClickListener {
            val intent = Intent(this@Dashboard_Quran.requireActivity(), Detail_Surrat_Quran::class.java)
            startActivity(intent)
        }

        return toogle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}