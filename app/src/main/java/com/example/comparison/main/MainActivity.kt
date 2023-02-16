package com.example.comparison.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparison.R
import com.example.comparison.base.BaseActivity
import com.example.comparison.comparison.ComparisonActivity
import com.example.comparison.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter

    private lateinit var mainPresenter: MainContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        // setTheme: android12 이전 splash screen
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        // installSplashScreen() : android12 이후 splash screen api 사용
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

//        setContentView(R.layout.activity_main)
        // binding 방식으로 개발했으면 setContentView도 binding.root로 변경해야함
        setContentView(binding.root)

        mainPresenter.setView(this)

        setFloatingButton()


        // recyclerview
        initRecyclerView()

    }

    override fun initPresenter() {
        mainPresenter = MainPresenter()
    }

    private fun initRecyclerView() {
        adapter = MainAdapter()
        adapter.datas.apply {
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름1", price = 200))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름2", price = 30000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름3", price = 40000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름4", price = 5000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름5", price = 6000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름1", price = 200))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름2", price = 30000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름3", price = 40000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름4", price = 5000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름5", price = 6000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름1", price = 200))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름2", price = 30000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름3", price = 40000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름4", price = 5000))
            add(MainData(img = R.drawable.ic_image_not_supported_24, name = "이름5", price = 6000))

        }
        binding.rvMain.layoutManager = GridLayoutManager(this@MainActivity, 3)
        binding.rvMain.adapter = this@MainActivity.adapter

    }

    override fun setFloatingButton() {

        val fab: View = findViewById(R.id.fb_main)
        fab.setOnClickListener {
            val et = EditText(this)
            et.gravity = Gravity.CENTER
            et.inputType = InputType.TYPE_CLASS_NUMBER
            val builder = AlertDialog.Builder(this)
                .setTitle("상품 url을 입력해주세요.")
                .setView(et)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                    Log.e("사용자가 입력한 url: ", et.text.toString())
                    mainPresenter.loadData(et.text.toString())

                })
            builder.show()
        }
    }

    override fun sendDataNextView(price: String) {
        val intentPrice = Intent(this, ComparisonActivity::class.java)
        intentPrice.putExtra("price", price)
        startActivity(intentPrice)

    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showToastMessage(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.dropView()
    }

}


