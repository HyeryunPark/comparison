package com.example.comparison.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparison.R
import com.example.comparison.WebviewActivity
import com.example.comparison.base.BaseActivity
import com.example.comparison.comparison.ComparisonActivity
import com.example.comparison.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter

    private lateinit var mainPresenter: MainContract.Presenter

    private var isFabOpen = false

    private lateinit var fb1: FloatingActionButton
    private lateinit var fb2: FloatingActionButton
    private lateinit var fab: FloatingActionButton

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateForward: Animation
    private lateinit var rotateBackward: Animation


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

        fb1 = findViewById(R.id.fb1)
        fb2 = findViewById(R.id.fb2)
        fab = findViewById(R.id.fb_main)

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward)

        // 플로팅버튼 클릭
        fab.setOnClickListener {

            Toast.makeText(this, "fab clicked", Toast.LENGTH_SHORT).show()

            animateFab()

        }
        // 플로팅버튼 클릭 이벤트 -> fb1 : webView 띄우기
        fb1.setOnClickListener {

            Toast.makeText(this, "fab1 clicked", Toast.LENGTH_SHORT).show()

            animateFab()

            val intent = Intent(this, WebviewActivity::class.java)
            startActivity(intent)

        }
        // 플로팅버튼 클릭 이벤트 -> fb2 : url 입력 Dialog 띄우기
        fb2.setOnClickListener {

            Toast.makeText(this, "fab2 clicked", Toast.LENGTH_SHORT).show()

            animateFab()

            val et = EditText(this)
            et.gravity = Gravity.CENTER
            et.inputType = InputType.TYPE_CLASS_TEXT
            val builder = AlertDialog.Builder(this)
                .setTitle("상품 url을 입력해주세요.")
                .setView(et)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                    Log.e("사용자가 입력한 url: ", et.text.toString())
//                    mainPresenter.loadData(et.text.toString())


                })
            builder.show()
        }

    }
    private fun animateFab() {
        if (isFabOpen) {

            fab.startAnimation(rotateBackward)
            fb1.startAnimation(fabClose)
            fb2.startAnimation(fabClose)
            fb1.isClickable = false
            fb2.isClickable = false

            isFabOpen = false

        } else {

            fab.startAnimation(rotateForward)
            fb1.startAnimation(fabOpen)
            fb2.startAnimation(fabOpen)
            fb1.isClickable = true
            fb2.isClickable = true

            isFabOpen = true

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


