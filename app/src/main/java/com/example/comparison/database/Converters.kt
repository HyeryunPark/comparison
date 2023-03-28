package com.example.comparison.database

import androidx.room.TypeConverter
import com.example.comparison.model.PricesData
import com.google.gson.Gson

class Converters {
/*
    @TypeConverter
    fun listToJson(value: List<String>?): String?{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>?{
        return Gson().fromJson(value, Array<String>::class.java)?.toList()
    }

*/
    @TypeConverter
    fun listToJson(value: List<PricesData>?): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<PricesData>::class.java).toList()
}