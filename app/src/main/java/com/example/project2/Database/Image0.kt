package com.example.project2.Database

import android.media.Image
import android.net.Uri
import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture")

data class Image0(
    @PrimaryKey
    var KEY:String,
    @ColumnInfo(name = "VALUE")
   // var value: String?=null,
    //var value: Uri?=null,    //ByteArray
    var value: ByteArray?=null,
  //  @ColumnInfo(name = "CONTENT TYPE")
  //  var content_type:String?=null,
    @ColumnInfo(name = "DESCRIPTION")
    var description:String?=null
    )