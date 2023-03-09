package com.example.comparison.model

import com.google.gson.annotations.SerializedName

data class GetMainData (
    @SerializedName("pcode")
    val p_code: Int ,

    @SerializedName("img_src")
    val img: String ,

    @SerializedName("name")
    val name: String ,

    @SerializedName("prices")
    val prices: List<PricesData>? = null
)