package com.example.comparison.main

import java.io.Serializable

data class MainData(
    val img: String,
    val name: String,
    val price: String
) : Serializable

