package com.example.comparison

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comparison.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
        // binding 방식으로 개발했으면 setContentView도 binding.root로 변경해야함
        setContentView(binding.root)

        //
        val search_view = findViewById<SearchView>(R.id.search_view)
        search_view.setOnClickListener {
            var intent = Intent(this, ComparisonActivity::class.java)
            startActivity(intent)
        }

        // recyclerview
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = MainAdapter()
        adapter.datas.apply {
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름1", price = 200))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름2", price = 30000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름3", price = 40000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름4", price = 5000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름5", price = 6000))
        }
        binding.rvMain.layoutManager = GridLayoutManager(this@MainActivity, 3)
        binding.rvMain.adapter = this@MainActivity.adapter

    }

}


