package com.example.androidflow.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.androidflow.pagining.MainPagingSource
import com.example.androidflow.repository.PostRepository
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.entity.FavouriteEntity
import com.example.androidflow.roomDB.entity.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository, private  val postDao: PostDao) : ViewModel() {

    private val _response: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val response: LiveData<List<PostEntity>> = _response
    val getDataAsLiveData=postRepository.getAllPostFromRoom
        .catch { e->
            Log.d("main", "${e.message}")
        }.asLiveData()


    val getAllPostLocal: Flow<List<PostEntity>> = postRepository.getAllPostFromRoom
        .flowOn(Dispatchers.Main)



    fun insertData(postList: List<PostEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.insert(postList)
        }
    }
    fun deleteAllPostTable() = viewModelScope.launch {
        postRepository.clearPostAllTable()
    }

    fun insertFavourite(postList: FavouriteEntity) = viewModelScope.launch {
        postRepository.insertFavourite(postList)
    }

    //Call Api and get Response
    fun getPost() {
        viewModelScope.launch {
            postRepository.getPostFromServer()
                .catch { e ->
                    Log.e("MainViewModel_ERROR", "getPost: ${e.message}")
                }.collect { response ->
                    _response.value = response
                }

        }
    }


    val getDataFromRoomWithOffset = Pager(
        PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
    ) {
        MainPagingSource(postDao)
    }.flow.cachedIn(viewModelScope)





}