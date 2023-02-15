package com.example.comparison.main

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.comparison.R
import com.example.comparison.comparison.ComparisonActivity

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    var datas = mutableListOf<MainData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(datas[position])

    }

    override fun getItemCount(): Int {
        Log.e("메인어뎁터", "getItemCount $datas.size")
        return datas.size
    }


    //    inner class ViewHolder(private val binding: ItemMainBinding) :
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val item_iv: ImageView = itemView.findViewById(R.id.item_iv)
        private val item_tv_name: TextView = itemView.findViewById(R.id.item_tv_name)
        private val item_tv_price: TextView = itemView.findViewById(R.id.item_tv_price)

        fun bind(item: MainData) {
//            binding.itemIv = item.img
            item_tv_name.text = item.name
            item_tv_price.text = item.price.toString()

            itemView.setOnClickListener {
                Log.e("item click!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", item.name+", "+item.price.toString())

                /*val intent = Intent(itemView.context, ComparisonActivity::class.java)
                intent.putExtra("data_name", item.name)
                intent.putExtra("data_price", item.price)
                startActivity(itemView.context, intent, null)*/

                Intent(itemView.context, ComparisonActivity::class.java).apply {
                    putExtra("data", item)
                }.run { itemView.context.startActivity(this) }
            }
        }
    }
}
