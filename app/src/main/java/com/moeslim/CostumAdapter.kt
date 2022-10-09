package com.moeslim

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moeslim.constans.ActionType
import com.moeslim.helper.PreferencesHelper
import com.moeslim.model.quran.ItemDetail

class CostumAdapter(val dataMorty: List<ItemDetail>): RecyclerView.Adapter<CostumAdapter.MyViewHolder>() {

    var selectedItemPosition: Int = -1

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardSurrah = view.findViewById<LinearLayout>(R.id.card_surrah_quran)
        val numSurrah = view.findViewById<TextView>(R.id.text_num_surrah)
        val nameSurrah = view.findViewById<TextView>(R.id.text_name_surrah)
        val fromSurrah = view.findViewById<TextView>(R.id.text_from_surrah)
        val ayahSurrah = view.findViewById<TextView>(R.id.text_ayah_surrah)
        val nameArabicSurrah = view.findViewById<TextView>(R.id.text_nameArab_surrah)
        val icon = view.findViewById<LinearLayout>(R.id.icon_ayah_surrah)
        val ayat = view.findViewById<TextView>(R.id.text_ayat)
        val strip = view.findViewById<TextView>(R.id.text_strip)
//        val surrah = view.findViewById<TextView>(R.id.text_surat_quran_recent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.quran_recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val context = holder.itemView.context
        var sharePreff = PreferencesHelper(holder.itemView.context)
        var morty = dataMorty.get(position)
        var recentSurrah = sharePreff.getString(ActionType.PREF_RECENT_SURRAH)

        holder.numSurrah.text = dataMorty.get(position).nomor
        holder.nameSurrah.text = dataMorty.get(position).nama
        holder.ayahSurrah.text = dataMorty.get(position).ayat.toString()
        holder.fromSurrah.text = dataMorty.get(position).type.toString().capitalize()
        holder.nameArabicSurrah.text = dataMorty.get(position).asma

        holder.itemView.setOnClickListener( View.OnClickListener(){
            selectedItemPosition = position
            notifyDataSetChanged()

        })
        if (selectedItemPosition == position) {
            if (recentSurrah !== null ) {
                if (recentSurrah == morty?.nama) {

                }
                else {
                    sharePreff.clearAction(ActionType.PREF_RECENT_AYYAH)
                }
            }
            holder.cardSurrah.setBackgroundResource(R.drawable.card_gradient)
            holder.icon.setBackgroundResource(R.drawable.ic_startwhiteicon)
            holder.numSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.nameSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.ayahSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.nameArabicSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.fromSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.ayat.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.strip.setTextColor(ContextCompat.getColor(context, R.color.white))
            // parse name surrah pref
            sharePreff.put(ActionType.PREF_RECENT_SURRAH, morty.nama.toString())
            //parse surrah
            val intent = Intent(context, Detail_Ayyah_Quran::class.java)
            intent.putExtra(ActionType.PREF_ID_SURRAH, morty.nomor)
            intent.putExtra(ActionType.PREF_DETAIL_SURRAH, morty.nama)
            intent.putExtra(ActionType.PREFF_DETAIL_FROM_SURRAH, morty.type.toString().capitalize())
            intent.putExtra(ActionType.PREFF_JUMLAH_AYYAH, morty.ayat.toString())
            context.startActivity(intent)
        }
        else {
            if (recentSurrah == morty.nama) {
                holder.cardSurrah.setBackgroundResource(R.drawable.card_gradient)
                holder.icon.setBackgroundResource(R.drawable.ic_startwhiteicon)
                holder.numSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.nameSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.ayahSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.nameArabicSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.fromSurrah.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.ayat.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.strip.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            else {
                holder.cardSurrah.setBackgroundResource(R.drawable.card_white)
                holder.icon.setBackgroundResource(R.drawable.ic_stariconmaincolor)
                holder.numSurrah.setTextColor(ContextCompat.getColor(context, R.color.main_color))
                holder.nameSurrah.setTextColor(ContextCompat.getColor(context, R.color.main_color))
                holder.nameArabicSurrah.setTextColor(ContextCompat.getColor(context, R.color.main_color))
                holder.ayahSurrah.setTextColor(ContextCompat.getColor(context, R.color.second_color))
                holder.fromSurrah.setTextColor(ContextCompat.getColor(context, R.color.second_color))
                holder.ayat.setTextColor(ContextCompat.getColor(context, R.color.second_color))
                holder.strip.setTextColor(ContextCompat.getColor(context, R.color.second_color))
            }
        }
    }

    override fun getItemCount(): Int {
        if (dataMorty !== null) {
            return dataMorty.size
        }
        return 0
    }

}