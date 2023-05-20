package com.example.androidflow.repository


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.androidflow.network.ApiService
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.database.PostDatabase
import com.example.androidflow.roomDB.entity.FavouriteEntity
import com.example.androidflow.roomDB.entity.PostEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val apiService: ApiService
) {
    //Get Data from API
    fun getPostFromServer(): Flow<List<PostEntity>> = flow {
        val response = apiService.getAllPosterImages()
        emit(response)
    }.flowOn(Dispatchers.IO)

    //Insert into RoomDB
    suspend fun insert(postList: List<PostEntity>) = withContext(Dispatchers.IO) {
        postDao.insert(postList)
    }

    //Delete all RoomDB
    suspend fun clearPostAllTable() = withContext(Dispatchers.IO) {
        postDao.deleteAllData()
    }

    //Add to favourite
    suspend fun insertFavourite(favouriteEntity: FavouriteEntity) = withContext(Dispatchers.IO) {
        postDao.insertFavorite(favouriteEntity)
    }

    //Get All Post From Room database
    val getAllPostFromRoom: Flow<List<PostEntity>> = postDao.getAllPosts()



}
