package com.example.comparison.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comparison.R
import com.example.comparison.database.MainInfo

class MainAdapter(val context: Context, var dataList: List<MainInfo>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
//    var datas = mutableListOf<MainData>()

    // 아이템 클릭 리스너 인터페이스
    interface itemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onDeleteClick(view: View, position: Int)
    }

    var itemClick: itemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(dataList[position])

        // 아이템 클릭 - 제품 상세보기
        if (itemClick != null) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                itemClick?.onItemClick(it, position)
                Log.e("item click!!!", "position: $position, datas: ${dataList[position]}")

            })
        }
        // 아이템 롱클릭 - 제품 삭제
        if (itemClick != null) {
            holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                itemClick?.onDeleteClick(it, position)
                Log.e("item long click!!!", position.toString())

                return@OnLongClickListener true
            })
        }

    }

    override fun getItemCount(): Int {
        Log.e("메인어뎁터", "getItemCount ${dataList.size}")
        return dataList.size
    }


    //    inner class ViewHolder(private val binding: ItemMainBinding) :
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemIv: ImageView = itemView.findViewById(R.id.item_iv)
        private val itemTvName: TextView = itemView.findViewById(R.id.item_tv_name)
        private val itemTvPrice: TextView = itemView.findViewById(R.id.item_tv_price)

        fun bind(item: MainInfo) {
            Log.e("img_src",item.img_src)
            Glide.with(context as MainActivity).load(item.img_src).into(itemIv)
            itemTvName.text = item.name
            itemTvPrice.text = item.price

            /*itemView.setOnClickListener {
                Log.e("item click!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", item.name + ", " + item.price.toString())

                val intent = Intent(itemView.context, ComparisonActivity::class.java)
                intent.putExtra("data_name", item.name)
                intent.putExtra("data_price", item.price)
                startActivity(itemView.context, intent, null)

                Intent(itemView.context, ComparisonActivity::class.java).apply {
                    putExtra("data", item)
                }.run { itemView.context.startActivity(this) }
            }*/

        }
    }


}
