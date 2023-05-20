package com.example.androidflow.network

import com.example.androidflow.roomDB.entity.PostEntity
import retrofit2.http.GET

interface ApiService {
    @GET(NetworkingConstants.URL_REPOSITORIES)
    suspend fun getAllPosterImages(): List<PostEntity>
}