package com.example.androidflow.viewmodel

import androidx.lifecycle.*
import com.example.androidflow.repository.FavRepositoryImpl
import com.example.androidflow.roomDB.entity.FavouriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor(private val favRepository: FavRepositoryImpl) : ViewModel() {
    fun getFullListFav(): Flow<List<FavouriteEntity>> = favRepository.getFavouriteFromRoom()


}