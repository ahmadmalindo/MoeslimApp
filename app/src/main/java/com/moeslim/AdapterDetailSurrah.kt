package com.moeslim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moeslim.constans.ActionType
import com.moeslim.helper.PreferencesHelper
import com.moeslim.model.quran.DataItem
import com.moeslim.model.quran.FilterAyyahQuran
import com.moeslim.model.quran.ResponseApiDetailDataQuran

class AdapterDetailSurrah(var dataMorty: ArrayList<DataItem>) : RecyclerView.Adapter<AdapterDetailSurrah.MyViewHolder>() {

    var selecetItemPosition: Int = -1

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ayyah = view.findViewById<TextView>(R.id.text_ayah_surrah)
        val translate = view.findViewById<TextView>(R.id.text_translate_ayyah)
        val numArabic = view.findViewById<TextView>(R.id.text_numArabic_surrah)
        val num = view.findViewById<TextView>(R.id.text_num_surrah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_quran_recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val context = holder.itemView.context
        val sharedPreff = PreferencesHelper(context)
        val morty = dataMorty.get(position)
        var recentAyyah = sharedPreff.getString(ActionType.PREF_RECENT_AYYAH)

        holder.ayyah.text = dataMorty.get(position).ar
        holder.translate.text = dataMorty.get(position).id
        holder.numArabic.text = dataMorty.get(position).nomor
        holder.num.text = dataMorty.get(position).nomor

        holder.itemView.setOnClickListener(View.OnClickListener {
            selecetItemPosition = position
            notifyDataSetChanged()
        })
        if (selecetItemPosition == position) {
            holder.ayyah.setTextColor(ContextCompat.getColor(context, R.color.third_color))
            //sharedPrefrence
            sharedPreff.put(ActionType.PREF_RECENT_AYYAH, morty.nomor.toString())
        } else {
            if (recentAyyah == morty?.nomor) {
                holder.ayyah.setTextColor(ContextCompat.getColor(context, R.color.third_color))
            }
            else {
                holder.ayyah.setTextColor(ContextCompat.getColor(context, R.color.main_color))
            }
        }
    }

    override fun getItemCount(): Int {
        return dataMorty.size
    }
}
