package com.example.comparison.main

import java.io.Serializable

data class MainData(
    val img: Int,
    val name: String,
    val price: Int
) : Serializable

