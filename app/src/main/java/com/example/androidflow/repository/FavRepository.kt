package com.example.androidflow.repository

import com.example.androidflow.roomDB.entity.FavoritesEntity
import kotlinx.coroutines.flow.Flow


interface FavRepository {
    fun getFavouriteFromRoom(): Flow<List<FavoritesEntity>>

}
