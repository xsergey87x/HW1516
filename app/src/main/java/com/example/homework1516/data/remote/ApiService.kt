package com.example.homework1516.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("offset") offset: String,
        @Query("limit") limit: String,
    ): Response<SearchResult>?

}