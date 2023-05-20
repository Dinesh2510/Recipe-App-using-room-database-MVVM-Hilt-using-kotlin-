package com.example.androidflow.di

import android.content.Context
import androidx.room.Room
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.database.PostDatabase

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
    fun providePostDb(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context, PostDatabase::class.java,
            PostDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDAO(postDatabase: PostDatabase): PostDao {
        return postDatabase.getPostDao()
    }
}