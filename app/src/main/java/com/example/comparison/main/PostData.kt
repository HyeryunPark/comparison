package com.example.comparison.main

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("url")
    val url:String = "",

    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body:String = ""
)






