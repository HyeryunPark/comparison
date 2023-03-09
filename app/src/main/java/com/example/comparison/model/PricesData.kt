package com.example.comparison.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PricesData(
    @SerializedName("date")
    val date: Int,
    @SerializedName("low_price")
    val low_price: String = "",
) : Serializable