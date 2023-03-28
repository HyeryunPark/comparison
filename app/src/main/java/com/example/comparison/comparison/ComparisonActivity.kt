package com.example.comparison.comparison

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.comparison.*
import com.example.comparison.base.BaseActivity
import com.example.comparison.comparison.model.PricesViewModel
import com.example.comparison.databinding.ActivityComparisonBinding
import com.example.comparison.main.MainData
import com.google.android.material.tabs.TabLayoutMediator


// ComparisonActivity.kt : 가격비교,가격변동,상품후기 등을 보여주는 페이지 (ViewPager2사용)
class ComparisonActivity : BaseActivity(), ComparisonContract.View {
    lateinit var binding: ActivityComparisonBinding

    private lateinit var comparisonPresenter: ComparisonContract.Presenter
    private val viewModel: PricesViewModel by viewModels()

    lateinit var datas: MainData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComparisonBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_comparison)
        setContentView(binding.root)

        comparisonPresenter.setView(this)

        // 툴바 선언
        val toolbar = findViewById<Toolbar>(R.id.toolbar_comparison)
        setSupportActionBar((toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ViewPager2
        initViewPager()

        // intent 로 넘어온 값
        val p_code = intent.getIntExtra("p_code",0)
        val img = intent.getStringExtra("img")
        val name = intent.getStringExtra("name")
        val price = intent.getIntExtra("price",0)
        Log.e("넘어온 intent name", name.toString())
        Log.e("넘어온 p_code", p_code.toString())
        Glide.with(this).load(img).into(binding.ivComparison)
        binding.tvName.text = name
        binding.tvPrice.text = price.toString()

        // viewModel
//        viewModel = ViewModelProvider(this).get(PricesViewModel::class.java)


    }

    override fun onDestroy() {
        super.onDestroy()
        comparisonPresenter.dropView()
    }

    override fun initPresenter() {
        comparisonPresenter = ComparisonPresenter()
    }

    private fun initViewPager() {
        Log.e("initViewPager", "시자아아아아아아ㅏㄱ")

        //ViewPager2 Adapter 셋팅
        val viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addFragment(Tab1Fragment())
        viewPager2Adapter.addFragment(Tab2Fragment())
        viewPager2Adapter.addFragment(Tab3Fragment())

        //Adapter 연결
        binding.vpViewpagerMain.apply {
            adapter = viewPager2Adapter
            Log.e("adapter", "연결되냐고")
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Log.e("adapter", "onPageSelected $position")

                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain) { tab, position ->
            Log.e("TabLayoutMediator", "ViewPager position: $position")
            when (position) {
                0 -> tab.text = "가격비교"
                1 -> tab.text = "그래프"
                2 -> tab.text = "상품후기"
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

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showToastMessage(msg: String) {
        TODO("Not yet implemented")
    }
}