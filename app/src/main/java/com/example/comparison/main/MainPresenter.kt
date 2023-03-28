package com.example.comparison.main

import android.content.Context
import android.util.Log
import com.example.comparison.database.MainDatabase
import com.example.comparison.database.MainInfo
import com.example.comparison.model.GetMainData
import com.example.comparison.network.RetrofitClient
import com.example.comparison.network.RetrofitInterface
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class MainPresenter : MainContract.Presenter {

    private var mainView: MainContract.View? = null
    private lateinit var db: MainDatabase

    private var mainAdapter: MainAdapter? = null


    override fun setView(view: MainContract.View) {
        mainView = view
    }

    override fun dropView() {
        mainView = null
    }

/*
    override fun loadData(p_code: Int) {
        val retrofit: Retrofit = RetrofitClient.getInstance()
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.getData(p_code).enqueue(object : Callback<MainInfo> {
            override fun onResponse(call: Call<MainInfo>, response: Response<MainInfo>) {
                if (response.isSuccessful) {
                    // onResponse 통신 성공시 Callback ( 메인스레드에서 작업하는 부분 (UI 작업 가능))

                    Log.e("onResponse 성공: ", Gson().toJson(response.body()))

                     val body = response.body()
                     body?.let {
                         for (i in 0 until body.size) {
                             var dataList: PostData = PostData()
                             dataList = body[i]

                             var addDataList:MainInfo = MainInfo(
                                 dataList.p_code,
                                 dataList.name,
                                 dataList.price,
                                 dataList.img_src
                             )
                             Log.e("get dataList: ", dataList.name)
                             addData(mainInfo = addDataList)
                         }
                     }
//                    mainView!!.sendDataNextView(price = response.body()?.price.toString(), img = response.body()?.img_src.toString())
                    addData(response.body()!!)

                } else {
                    // onResponse 가 무조건 성공 응답이 아니기에 확인 필요 (응답 코드 3xx, 4xx 호출)
                    Log.e("onResponse 실패: ", "")
                }
            }

            override fun onFailure(call: Call<MainInfo>, t: Throwable) {
                // onFailure 통신 실패시 Callback ( 인터넷 끊김, 예외 발생 등 시스템적인 이유 )
                Log.e("onFailure: ", t.toString())
            }

        })
    }
*/

    override fun loadData(p_url: String) {
        val retrofit: Retrofit = RetrofitClient.getInstance()
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.getDataInfo(p_url).enqueue(object : Callback<GetMainData> {
            override fun onResponse(call: Call<GetMainData>, response: Response<GetMainData>) {
                if (response.isSuccessful) {
                    // onResponse 통신 성공시 Callback ( 메인스레드에서 작업하는 부분 (UI 작업 가능))
                    Log.e("onResponse 성공: ", Gson().toJson(response.body()))

                    val now = System.currentTimeMillis()
                    val date = SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(now)
                    Log.e("오늘 날짜", date)

                    val body = response.body()
                    //  body.prices[0] : 가장 최근 날짜와 최저가
                    Log.e("body.prices[0]", body!!.prices!![0].toString())
                    Log.e("body.prices[0].low_price", body.prices!![0].low_price.toString())

                    val addData: MainInfo = MainInfo(
                        p_code = body.p_code,
                        img_src = body.img,
                        name = body.name,
                        price = body.prices[0].low_price.toInt()
                    )
                    Log.e("addData",addData.toString())

                    addData(addData)
                    mainAdapter?.addItem(addData)
//                    mainView?.sendDataNextView(
//                        img = addData.img_src,
//                        name = addData.name,
//                        price = addData.price,
//                        prices =
//                    )

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

    override fun setDatabase(context: Context) {
        db = MainDatabase.buildDatabase(context)!!
    }

    override fun loadAllData(dataList: List<MainInfo>) {

        CoroutineScope(Dispatchers.IO).launch {
            // db에 저장된 데이터 모두 불러오기
            db.MainDao().loadAll()
            Log.e("db", db.MainDao().loadAll().toString())

        }
    }

    override fun addData(mainInfo: MainInfo) {

        // 데이터베이스 작업은 main thread 에서 처리할 수 없음. coroutine 또는 thread 를 사용해서 작업해야함
        CoroutineScope(Dispatchers.IO).launch {
            // db에 데이터 저장하기
            db.MainDao().insert(mainInfo = mainInfo)
            Log.e("db insert [SUCCESS]", mainInfo.name)
        }
    }

    override fun deleteData(mainInfo: MainInfo) {

        CoroutineScope(Dispatchers.IO).launch {
            // db에 저장된 데이터 삭제
            db.MainDao().delete(mainInfo = mainInfo)
            Log.e("db delete [SUCCESS]", mainInfo.name)

        }

    }
}
