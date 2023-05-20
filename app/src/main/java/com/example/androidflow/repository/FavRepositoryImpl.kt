package com.example.androidflow.repository

import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.entity.FavouriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FavRepositoryImpl @Inject constructor(
    private val postDao: PostDao
) : FavRepository {
    override fun getFavouriteFromRoom(): Flow<List<FavouriteEntity>> = postDao.getAllFavouriteData()
/*
    override fun getFavouriteFromRoom(): Flow<List<FavouriteEntity>> {
        return flow<List<FavouriteEntity>> {

            // get the comment Data from the api
            val comment=postDao.getAllFavouriteData()

            // Emit this data wrapped in
            // the helper class [CommentApiState]
            emit(comment)
        }.flowOn(Dispatchers.IO)
    }
*/

}
