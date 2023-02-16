package com.example.comparison.main

import android.util.Log
import com.example.comparison.network.RetrofitClient
import com.example.comparison.network.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainPresenter : MainContract.Presenter {

    private var mainView: MainContract.View? = null

    override fun setView(view: MainContract.View) {
        mainView = view
    }

    override fun dropView() {
        mainView = null
    }


    override fun loadData(p_code: String) {
        val retrofit: Retrofit = RetrofitClient.getInstance()
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.getData(p_code).enqueue(object : Callback<PostData> {
            override fun onResponse(call: Call<PostData>, response: Response<PostData>) {
                if (response.isSuccessful) {
                    // onResponse 통신 성공시 Callback ( 메인스레드에서 작업하는 부분 (UI 작업 가능))

                    Log.e("onResponse 성공 --price : ", response.body()?.price.toString())
                    Log.e("onResponse 성공 --img_src : ", response.body()?.img_src.toString())

                    mainView!!.sendDataNextView(price = response.body()?.price.toString())


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

}