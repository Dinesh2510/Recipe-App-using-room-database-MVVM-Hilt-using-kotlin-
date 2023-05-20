package com.example.androidflow.di

import android.content.Context
import androidx.room.Room
import com.example.androidflow.network.NetworkingConstants.DATABASE_NAME
import com.example.androidflow.roomDB.ResultConverter
import com.example.androidflow.roomDB.dao.FoodRecipeDao
import com.example.androidflow.roomDB.database.FoodDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun providePostDb(@ApplicationContext context: Context): FoodDatabase {
        return Room.databaseBuilder(
            context, FoodDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDAO(foodDb: FoodDatabase): FoodRecipeDao {
        return foodDb.getFoodRecipeDao()
    }
}