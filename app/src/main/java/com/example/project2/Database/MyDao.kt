package com.example.project2.Database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MyDao{
    @Insert
    fun addImage(image:Image0)
}