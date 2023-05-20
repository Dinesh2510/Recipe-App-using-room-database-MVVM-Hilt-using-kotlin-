package com.example.androidflow.roomDB.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "postsTb")
data class PostEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Pid") var Pid: Int,
    @SerializedName("albumId") var albumId: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("url") var url: String,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String


)