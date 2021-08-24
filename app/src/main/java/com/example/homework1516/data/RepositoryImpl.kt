package com.example.homework1516.data


import com.example.homework1516.presentation.Repository
import com.example.homework1516.data.remote.Data
import com.example.homework1516.data.remote.ApiService
import com.example.homework1516.API_KEY
import com.example.homework1516.Limit_Item

class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun performSearch(query: String, offset: String): List<Data> {
        val response = apiService.search(API_KEY, query, offset, Limit_Item)
        return response?.body()?.data ?: emptyList()
    }

}