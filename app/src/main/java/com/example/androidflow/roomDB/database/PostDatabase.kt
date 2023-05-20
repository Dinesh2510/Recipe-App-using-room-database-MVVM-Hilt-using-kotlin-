package com.example.androidflow.roomDB.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.entity.FavouriteEntity
import com.example.androidflow.roomDB.entity.PostEntity

@Database(entities = [PostEntity::class, FavouriteEntity::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao


    companion object {
        const val DATABASE_NAME: String = "Post_DB"
    }
}