package com.example.comparison.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_info")
data class MainInfo(
    @ColumnInfo
    val name: String,
    @PrimaryKey
    @SerializedName("pcode")
    val p_code: Int,
    @ColumnInfo
    val price: String,
    @ColumnInfo
    val img_src: String
)
