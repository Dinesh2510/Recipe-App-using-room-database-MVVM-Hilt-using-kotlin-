package com.example.androidflow.network

import com.example.androidflow.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
   /* @GET(NetworkingConstants.URL_REPOSITORIES)
    suspend fun getAllPosterImages(): List<PostEntity>*/

    @GET(NetworkingConstants.URL_REPOSITORIES)
    suspend fun getRecipes(): Response<FoodRecipe>
}