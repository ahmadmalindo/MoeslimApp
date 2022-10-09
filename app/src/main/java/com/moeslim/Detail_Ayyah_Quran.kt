package com.moeslim

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moeslim.constans.ActionType
import com.moeslim.constans.ApiQuran
import com.moeslim.model.quran.DataItem
import com.moeslim.model.quran.ResponseApiDetailDataQuran
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail_Ayyah_Quran : AppCompatActivity() {

    private var adapterDetailSurrah: AdapterDetailSurrah? = null
    private lateinit var adapter: RecyclerView
    private lateinit var adapter2: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ayyah_quran)

        // back to dashboard Quran
        val btn = findViewById<LinearLayout>(R.id.btn_back)
        btn.setOnClickListener {
            val intent = Intent(this, Detail_Surrat_Quran::class.java)
            startActivity(intent)
        }
        // get intent detail surrah
        val postId = intent.getStringExtra(ActionType.PREF_ID_SURRAH).toString().toInt()
        val detailSurrah = intent.getStringExtra(ActionType.PREF_DETAIL_SURRAH)
        val fromSurrah = intent.getStringExtra(ActionType.PREFF_DETAIL_FROM_SURRAH)
        val jumlahAyyah = intent.getStringExtra(ActionType.PREFF_JUMLAH_AYYAH)
        // set detail surrah
        val nameSurrah = findViewById<TextView>(R.id.text_name_surrah)
        val fromNameSurrah = findViewById<TextView>(R.id.text_from_surrah)
        val ayyahnameSurrah = findViewById<TextView>(R.id.text_ayah_surrah)

        nameSurrah.setText("${detailSurrah}")
        fromNameSurrah.setText("${fromSurrah}")
        ayyahnameSurrah.setText("${jumlahAyyah}")
        // get api detail surrah
        getListMorty(postId)

        // search ayyah
        val btnSearch = findViewById<LinearLayout>(R.id.btn_search)
        val textDetailSurrah = findViewById<LinearLayout>(R.id.text_detail_surrah)
        val inputSearch = findViewById<EditText>(R.id.input_search)
        val btnClose = findViewById<LinearLayout>(R.id.btn_close)

        btnSearch.setOnClickListener {
            btn.visibility = View.INVISIBLE
            textDetailSurrah.visibility = View.INVISIBLE
            btnSearch.visibility = View.INVISIBLE

            inputSearch.visibility = View.VISIBLE
            btnClose.visibility = View.VISIBLE
        }

        btnClose.setOnClickListener {
            btn.visibility = View.VISIBLE
            textDetailSurrah.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE

            inputSearch.visibility = View.INVISIBLE
            btnClose.visibility = View.INVISIBLE
        }
    }

    private fun getListMorty(postId: Int) {
        val load = findViewById<ProgressBar>(R.id.activity_indicator)
        val inputSearch = findViewById<EditText>(R.id.input_search)
        adapter = findViewById(R.id.detail_quran_recycler)
        adapter2 = findViewById(R.id.detail_quran_recycler_2)

        adapter.layoutManager = LinearLayoutManager(this)
        adapter2.layoutManager = LinearLayoutManager(this)

        if (postId !== null) {
            ApiQuran.getServiceDataAllQuran().getDetailApiAyyahQuran(id = postId)
                .enqueue(object : Callback<ResponseApiDetailDataQuran> {
                    override fun onResponse(call: Call<ResponseApiDetailDataQuran>, response: Response<ResponseApiDetailDataQuran>) {
                        if (response.isSuccessful) {
                            val responMorty = response.body()
                            val dataMorty = responMorty?.data
                            adapterDetailSurrah = dataMorty?.let { AdapterDetailSurrah(it) }
                            adapter.adapter = adapterDetailSurrah
                            adapterDetailSurrah?.notifyDataSetChanged()
                            load.visibility = View.GONE
                            // search filter
                            inputSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH
                            inputSearch.addTextChangedListener(object : TextWatcher {
                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                                }

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                                }

                                override fun afterTextChanged(s: Editable?) {
                                    if (s!!.isEmpty()) {
                                        adapter.visibility = View.VISIBLE
                                        adapter2.visibility = View.GONE
                                    }
                                    else if (s.length > 0){
                                        val filter = dataMorty?.filter {
                                            it.nomor!!.contains("$s", true) ||
                                            it.id!!.contains("$s", true) ||
                                            it.ar!!.contains("$s", true)
                                        }
                                        adapterDetailSurrah = AdapterDetailSurrah(filter as ArrayList<DataItem>)
                                        if (s.isNotEmpty()) {
                                            adapter2.visibility = View.VISIBLE
                                            adapter2.adapter = adapterDetailSurrah
                                            adapter.visibility = View.GONE
                                        }
                                        else {
                                            Toast.makeText(this@Detail_Ayyah_Quran, "kosong", Toast.LENGTH_SHORT).show()
                                            adapter.visibility = View.VISIBLE
                                            adapter2.visibility = View.INVISIBLE

                                        }
                                    }
                                }
                            })
                        }
                    }
                    override fun onFailure(call: Call<ResponseApiDetailDataQuran>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
        else {
            Toast.makeText(this, "id kosong", Toast.LENGTH_SHORT).show()
        }
    }
}



