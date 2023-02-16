package com.example.comparison.main

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("price")
    val price: String = "",
    @SerializedName("img_src")
    val img_src: String = ""

)






