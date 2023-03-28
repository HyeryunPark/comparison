package com.example.comparison.comparison

import android.util.Log
import com.example.comparison.model.GetMainData
import com.example.comparison.model.PricesData
import com.example.comparison.network.RetrofitClient
import com.example.comparison.network.RetrofitInterface
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ComparisonPresenter : ComparisonContract.Presenter {

    private var comparisonView: ComparisonContract.View? = null
    private var tab1Adapter: Tab1Adapter? = null


    override fun setView(view: ComparisonContract.View) {
        comparisonView = view
    }

    override fun dropView() {
        comparisonView = null
    }

    // 사용자가 선택한 상품의 p_code 를 서버로 보내 상품 정보를 받아오는 함수
    override fun loadData(p_code: Int) {
        val retrofit: Retrofit = RetrofitClient.getInstance()
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.getDataInfoPCode(p_code = p_code)
            .enqueue(object : Callback<GetMainData> {
                override fun onResponse(call: Call<GetMainData>, response: Response<GetMainData>) {
                    if (response.isSuccessful) {
                        // onResponse 통신 성공시 Callback ( 메인스레드에서 작업하는 부분 (UI 작업 가능))
                        Log.e("onResponse 성공: ", Gson().toJson(response.body()))

                        val body = response.body()
                        body.let {
                            for (i in body!!.prices.indices) {
                                val date = body.prices[i].date
                                val low_price = body.prices[i].low_price

                                val addData = PricesData(
                                    date = date,
                                    low_price = low_price
                                )

                                tab1Adapter?.addItem(addData)
                                Log.e("addData", addData.toString())


                            }
                        }

                    } else {
                        // onResponse 가 무조건 성공 응답이 아니기에 확인 필요 (응답 코드 3xx, 4xx 호출)
                        Log.e("onResponse 실패: ", "")
                    }
                }

                override fun onFailure(call: Call<GetMainData>, t: Throwable) {
                    // onFailure 통신 실패시 Callback ( 인터넷 끊김, 예외 발생 등 시스템적인 이유 )
                    Log.e("onFailure: ", t.toString())
                }

            })


    }

}