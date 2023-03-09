package com.example.comparison.main

import java.io.Serializable

data class MainData(
    val p_code: Int,
    val img: String,
    val name: String,
    val price: Int
) : Serializable

