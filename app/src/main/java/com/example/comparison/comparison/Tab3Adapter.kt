package com.example.comparison.comparison

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comparison.R

class Tab3Adapter : RecyclerView.Adapter<Tab3Adapter.ViewHolder>() {
    private val list = listOf<Int>(1, 2, 3, 4, 5, 6)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tab3, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        Log.e("tab3adapter", "getItemCount ${list.size}")

        return list.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.review_date)

        fun bind(position: Int){
            textView.text = "Text $position"
        }
    }
}