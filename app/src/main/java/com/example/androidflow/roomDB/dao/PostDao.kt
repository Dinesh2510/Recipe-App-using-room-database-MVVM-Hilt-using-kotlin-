package com.example.androidflow.roomDB.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidflow.roomDB.entity.FavouriteEntity
import com.example.androidflow.roomDB.entity.PostEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postList: List<PostEntity>)

    @Query("SELECT * FROM postsTb ORDER BY id ASC")
    fun getAllPosts(): Flow<List<PostEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(model: FavouriteEntity)

    @Query("SELECT * FROM favoriteTb")
    fun getAllFavouriteData(): Flow<List<FavouriteEntity>>


    @Query("SELECT EXISTS (SELECT 1 FROM favoriteTb WHERE id = :id)")
    fun isFavorite(id: Int): Boolean

    @Query("DELETE FROM favoriteTb WHERE id = :id")
    fun deleteData(id: Int)

    @Query("DELETE FROM postsTb")
    fun deleteAllData()

    @Query("SELECT COUNT (Pid) FROM postsTb")
    fun getRowCount(): Int



    @Query("SELECT * FROM postsTb ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<PostEntity>
}