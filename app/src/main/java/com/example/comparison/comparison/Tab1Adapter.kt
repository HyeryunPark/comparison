package com.example.comparison.comparison

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comparison.R
import com.example.comparison.model.PricesData
import java.text.DecimalFormat

class Tab1Adapter(private val list: MutableList<PricesData>) :
    RecyclerView.Adapter<Tab1Adapter.ViewHolder>() {

    fun addItem(item: PricesData) {
        if (list != null) {
            list.add(item)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tab1, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        Log.e("Tab1Adapter", "getItemCount ${list.size}")

        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tab1_tv_date: TextView = view.findViewById(R.id.tab1_tv_date)
        private val tab1_tv_price: TextView = view.findViewById(R.id.tab1_tv_price)

        fun bind(item: PricesData) {
//            tab1_tv_date.text = "Text $position"
//            tab1_tv_price.text = "Text $position"
            val year = item.date.toString().substring(0, 4)
            val month = item.date.toString().substring(4, 6)
            val day = item.date.toString().substring(6, 8)
            val dateToString = year + "년 " + month + "월 " + day + "일"

            val priceFormat = DecimalFormat("#,###")
            tab1_tv_date.text = dateToString
            tab1_tv_price.text = priceFormat.format(item.low_price)


        }
    }
}