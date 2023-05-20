package com.example.androidflow.repository


import android.util.Log
import com.example.androidflow.models.FoodRecipe
import com.example.androidflow.models.ResultListing
import com.example.androidflow.network.ApiService
import com.example.androidflow.roomDB.dao.FoodRecipeDao
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class RecipesRepository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}