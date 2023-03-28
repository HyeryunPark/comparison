package com.example.comparison.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MainDao {

    @Query("SELECT * FROM product_info")
    fun loadAll(): List<MainInfo>

    @Insert
    fun insert(mainInfo: MainInfo)

    @Query("DELETE FROM product_info")
    fun deleteAll()

    @Delete
    fun delete(mainInfo: MainInfo)
}
