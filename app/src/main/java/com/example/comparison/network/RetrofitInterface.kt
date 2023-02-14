package com.example.comparison.network

import com.example.comparison.main.PostData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// 서버에서 호출할 메서드를 선언하는 인터페이스
interface RetrofitInterface {

    @GET("posts/{post}")
    fun getPosts(@Path("post") post: String): Call<PostData>




}