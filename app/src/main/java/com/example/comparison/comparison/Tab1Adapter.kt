package com.example.comparison.comparison

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comparison.R

class Tab1Adapter : RecyclerView.Adapter<Tab1Adapter.ViewHolder>() {

    private val list = listOf<Int>(1,2,3,4,56,7,8,9,45,2,6,5,7,8,9,8,7,8,9,8)

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
        private val tab1_tv_delivery: TextView = view.findViewById(R.id.tab1_tv_delivery)
        private val tab1_tv_price: TextView = view.findViewById(R.id.tab1_tv_price)

        fun bind(position: Int) {
            tab1_tv_delivery.text = "Text $position"
            tab1_tv_price.text = "Text $position"
        }
    }
}