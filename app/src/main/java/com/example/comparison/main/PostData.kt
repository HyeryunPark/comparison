package com.example.comparison.main

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pcode")
    val p_code: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("img_src")
    val img_src: String = ""

)






