package com.example.comparison.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MainInfo::class], version = 2)
abstract class MainDatabase : RoomDatabase() {
    abstract fun MainDao(): MainDao

    companion object {
        private var INSTANCE: MainDatabase? = null

        fun buildDatabase(context: Context): MainDatabase? {
            if (INSTANCE == null) {
                synchronized(MainDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context, MainDatabase::class.java, "comparison"
                    )
                        .addMigrations(migration_1_2)
                        .build()

                }
            }
            return INSTANCE
        }

        fun deleteInstance() {
            INSTANCE = null
        }

        // 마이그레이션
        //Migration version 이 1에서 2로 변경 되었다는 의미
        val migration_1_2 = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //만약, 테이블이 추가 되었다면 어떤 테이블이 추가 되었는지 알려주는 query문장이 필요
//                database.execSQL("CREATE TABLE 'REVIEW' ('id' INTEGER, 'review' TEXT, " + "PRIMARY KEY('id'))")
            }
        }

    }
}