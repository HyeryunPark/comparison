package com.example.comparison

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.comparison.databinding.ActivityComparisonBinding
import com.google.android.material.tabs.TabLayoutMediator


// ComparisonActivity.kt : 가격비교,가격변동,상품후기 등을 보여주는 페이지 (ViewPager2사용)
class ComparisonActivity : AppCompatActivity() {
    lateinit var binding: ActivityComparisonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComparisonBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_comparison)

        // 툴바 선언
        val toolbar = findViewById<Toolbar>(R.id.toolbar_comparison)
        setSupportActionBar((toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ViewPager2
        initViewPager()

    }

    private fun initViewPager() {
        Log.e("initViewPager", "시자아아아아아아ㅏㄱ")

        //ViewPager2 Adapter 셋팅
        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addFragment(Tab1Fragment())
        viewPager2Adapter.addFragment(Tab2Fragment())
        viewPager2Adapter.addFragment(Tab3Fragment())

        //Adapter 연결
        binding.vpViewpagerMain.apply {
            adapter = viewPager2Adapter
            Log.e("adapter","연결되냐고")
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Log.e("adapter","onPageSelected $position")

                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain) { tab, position ->
            Log.e("TabLayoutMediator", "ViewPager position: $position")
            when (position) {
                0 -> tab.text = "Tab1"
                1 -> tab.text = "Tab2"
                2 -> tab.text = "Tab3"
            }
        }.attach()
    }

    // toolbar backbutton
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}