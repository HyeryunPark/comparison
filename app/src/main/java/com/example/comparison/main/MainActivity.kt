package com.example.comparison.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparison.comparison.ComparisonActivity
import com.example.comparison.R
import com.example.comparison.base.BaseActivity
import com.example.comparison.databinding.ActivityMainBinding
import com.example.comparison.network.RetrofitClient
import com.example.comparison.network.RetrofitInterface
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter

    private lateinit var mainPresenter: MainContract.Presenter

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface: RetrofitInterface

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

//
/*        val search_view = findViewById<SearchView>(R.id.search_view)
        search_view.setOnClickListener {
            var intent = Intent(this, ComparisonActivity::class.java)
            startActivity(intent)
        }*/

        val fab: View = findViewById(R.id.fb_main)
        fab.setOnClickListener {
            val et = EditText(this)
            et.gravity = Gravity.CENTER
            val builder = AlertDialog.Builder(this)
                .setTitle("상품 url을 입력해주세요.")
                .setView(et)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(this, et.text, Toast.LENGTH_SHORT).show()
                    Log.e("사용자가 입력한 url: ", et.text.toString())
                })
            builder.show()
        }


        // recyclerview
        initRecyclerView()

        // retrofit
        initRetrofit()
        loadData()

    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.dropView()
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
        }
        binding.rvMain.layoutManager = GridLayoutManager(this@MainActivity, 3)
        binding.rvMain.adapter = this@MainActivity.adapter

    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
    }

    private fun loadData() {
        retrofitInterface.getPosts("1").enqueue(object : Callback<PostData> {
            override fun onResponse(call: Call<PostData>, response: Response<PostData>) {
                if (response.isSuccessful) {
                    // onResponse 통신 성공시 Callback ( 메인스레드에서 작업하는 부분 (UI 작업 가능))
                    Log.e("onResponse 성공: ", Gson().toJson(response.body()))

                } else {
                    // onResponse 가 무조건 성공 응답이 아니기에 확인 필요 (응답 코드 3xx, 4xx 호출)
                    Log.e("onResponse 실패: ", "")
                }

            }

            override fun onFailure(call: Call<PostData>, t: Throwable) {
                // onFailure 통신 실패시 Callback ( 인터넷 끊김, 예외 발생 등 시스템적인 이유 )
                Log.e("onFailure: ", t.toString())
            }

        })

    }


    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showToastMessage(msg: String) {
        TODO("Not yet implemented")
    }

}


