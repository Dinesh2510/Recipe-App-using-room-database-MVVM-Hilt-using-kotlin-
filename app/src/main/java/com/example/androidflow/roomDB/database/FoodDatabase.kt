package com.example.androidflow.roomDB.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidflow.models.ResultEntity
import com.example.androidflow.models.ResultListing
import com.example.androidflow.roomDB.ResultConverter
import com.example.androidflow.roomDB.dao.FoodRecipeDao
import com.example.androidflow.roomDB.entity.FavoritesEntity
@Database(
    entities = [FavoritesEntity::class, ResultListing::class, ResultEntity::class],
    version = 1,
    exportSchema = false
)

abstract class FoodDatabase : RoomDatabase() {

    abstract fun getFoodRecipeDao(): FoodRecipeDao

}