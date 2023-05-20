package com.example.androidflow.repository

import android.util.Log
import com.example.androidflow.models.FoodRecipe
import com.example.androidflow.models.ResultListing
import com.example.androidflow.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {


    suspend fun getRecipesFromServer(): Response<FoodRecipe> {
        return apiService.getRecipes()
    }


}