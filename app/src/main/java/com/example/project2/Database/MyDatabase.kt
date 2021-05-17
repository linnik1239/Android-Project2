package com.example.project2.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Image0::class],version = 1)
abstract class MyDatabase : RoomDatabase(){
    abstract fun getDao():MyDao
}