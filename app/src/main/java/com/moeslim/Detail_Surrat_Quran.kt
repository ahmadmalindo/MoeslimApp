package com.moeslim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moeslim.constans.ActionType
import com.moeslim.constans.ApiQuran
import com.moeslim.helper.PreferencesHelper
import com.moeslim.model.quran.ItemDetail
import com.moeslim.model.quran.ResponseApiDataAllQuran
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail_Surrat_Quran : AppCompatActivity() {

    private lateinit var adapter: RecyclerView
    private lateinit var adapter2: RecyclerView
    private var costumAdapter: CostumAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_surrat_quran)

        adapter = findViewById(R.id.detail_quran_recycler)
        adapter2 = findViewById(R.id.detail_quran_recycler_2)
        adapter.layoutManager = LinearLayoutManager(this)
        adapter2.layoutManager = LinearLayoutManager(this)

        // back dashboard
        val btnBack = findViewById<LinearLayout>(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        //get Api
        ApiQuran.getServiceDataAllQuran().getApiQuran()
            .enqueue(object : Callback<ResponseApiDataAllQuran> {
                override fun onResponse(call: Call<ResponseApiDataAllQuran>, response: Response<ResponseApiDataAllQuran>) {
                    if (response.isSuccessful) {
                        val respon = response.body()
                        val dataMorty = respon?.data
                        costumAdapter = dataMorty?.let { CostumAdapter(it) }
                        adapter.adapter = costumAdapter

                        val input = findViewById<EditText>(R.id.input_search)
                        input.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                            }

                            override fun afterTextChanged(s: Editable?) {
                                if (s!!.isEmpty()) {
                                    adapter.visibility = View.VISIBLE
                                    adapter2.visibility = View.GONE
                                }
                                else {
                                    val filter = dataMorty?.filter {
                                        it.nama!!.contains("$s", true) ||
                                        it.type!!.contains("$s", true) ||
                                        it.nomor!!.contains("$s", true)
                                    }
                                    costumAdapter = CostumAdapter(filter as List<ItemDetail>)

                                    if (s.isNotEmpty()) {
                                        adapter2.visibility = View.VISIBLE
                                        adapter2.adapter = costumAdapter
                                        adapter.visibility = View.GONE
                                    }
                                    else {
                                        adapter.visibility = View.VISIBLE
                                        adapter2.visibility = View.GONE
                                    }
                                }
                            }

                        })
                    }
                }

                override fun onFailure(call: Call<ResponseApiDataAllQuran>, t: Throwable) {
                    Toast.makeText(this@Detail_Surrat_Quran, "gagal", Toast.LENGTH_SHORT).show()
                }

            })

        // search ayyah
        val btn = findViewById<LinearLayout>(R.id.btn_back)
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
}