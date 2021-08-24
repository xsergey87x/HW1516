package com.example.homework1516.presentation

import com.example.homework1516.data.remote.Data

interface Repository {

    suspend fun performSearch(query: String, offset: String): List<Data>

}