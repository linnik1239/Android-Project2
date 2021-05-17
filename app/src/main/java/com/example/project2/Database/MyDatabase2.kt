package com.example.project2.Database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class MyDatabase2(
    var KEY:String,
    var VALUE: ByteArray?=null,
    var DESCRIPTOIN:String?=null
)