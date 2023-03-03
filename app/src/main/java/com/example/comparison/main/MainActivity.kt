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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparison.R
import com.example.comparison.WebviewActivity
import com.example.comparison.base.BaseActivity
import com.example.comparison.comparison.ComparisonActivity
import com.example.comparison.database.MainDao
import com.example.comparison.database.MainDatabase
import com.example.comparison.database.MainInfo
import com.example.comparison.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    private var dataList = listOf<MainInfo>()

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

        // DB
        mainPresenter.setDatabase(this)

        setFloatingButton()

        // recyclerview
        initRecyclerView()
        itemClick()


    }

    override fun initPresenter() {
        mainPresenter = MainPresenter()
    }

    private fun initRecyclerView() {
        adapter = MainAdapter(this, dataList)

        CoroutineScope(Dispatchers.IO).launch {
            val db = MainDatabase.buildDatabase(this@MainActivity)
            dataList = db?.MainDao()?.loadAll()!!
            adapter.dataList = dataList
            Log.e("db", db.MainDao().loadAll().toString())
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
//            Toast.makeText(this, "fab clicked", Toast.LENGTH_SHORT).show()


            animateFab()

        }
        // 플로팅버튼 클릭 이벤트 -> fb1 : webView 띄우기
        fb1.setOnClickListener {
//            Toast.makeText(this, "fab1 clicked", Toast.LENGTH_SHORT).show()


            animateFab()

            val intent = Intent(this, WebviewActivity::class.java)
            startActivity(intent)

        }
        // 플로팅버튼 클릭 이벤트 -> fb2 : url 입력 Dialog 띄우기
        fb2.setOnClickListener {
//            Toast.makeText(this, "fab2 clicked", Toast.LENGTH_SHORT).show()


            animateFab()

            val et = EditText(this)
            et.gravity = Gravity.CENTER
            et.inputType = InputType.TYPE_CLASS_TEXT
            val builder = AlertDialog.Builder(this)
                .setTitle("상품 url을 입력해주세요.")
                .setView(et)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                    val splitUrl = et.text.split("/")
                    val p_url = splitUrl[3]
                    Log.e("splitUrl[3] :", p_url)
                    Log.e("사용자가 입력한 url: ", et.text.toString())
//                    mainPresenter.loadData(et.text.toString().toInt())
                    mainPresenter.loadData(p_url)

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

    override fun sendDataNextView(img: String, name: String, price: String) {
        val intentData = Intent(this, ComparisonActivity::class.java)
        intentData.putExtra("img", img)
        intentData.putExtra("name", name)
        intentData.putExtra("price", price)
        startActivity(intentData)

    }

    // recyclerview item - long click 이벤트 처리 (아이템 삭제)
    private fun itemClick() {

        adapter.itemClick = object : MainAdapter.itemClickListener {

            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, ComparisonActivity::class.java)
                // intent.putExtra("data", adapter.datas[position])
                intent.putExtra("img", adapter.dataList[position].img_src)
                intent.putExtra("name", adapter.dataList[position].name)
                intent.putExtra("price", adapter.dataList[position].price)
                startActivity(intent)

            }

            override fun onItemLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("삭제하시겠습니까?")
                    .setPositiveButton("네", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            mainPresenter.deleteData(mainInfo = adapter.dataList[position])
                            Log.e("데이터삭제하기", adapter.dataList[position].toString())
//                            adapter.datas.removeAt(position)
                            adapter.notifyItemRemoved(position)

                        }
                    })
                    .setNegativeButton("아니요", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }
                    })
                    .create()
                    .show()
            }

        }

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


