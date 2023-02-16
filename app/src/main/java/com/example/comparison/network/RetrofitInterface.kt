package com.example.comparison.network

import com.example.comparison.main.MainData
import com.example.comparison.main.PostData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// 서버에서 호출할 메서드를 선언하는 인터페이스
interface RetrofitInterface {

    @GET("posts/{post}")
    fun getPosts(@Path("post") post: String): Call<PostData>

//    https://port-0-node-3a9t2ble0n86ua.sel3.cloudtype.app/crawl?pcode='상품번호'
    @GET("crawl")
    fun getData(@Query("pcode") p_code: String): Call<PostData>




}