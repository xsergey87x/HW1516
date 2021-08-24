package com.example.homework1516.di

import com.example.homework1516.data.RepositoryImpl
import com.example.homework1516.data.remote.ApiService
import com.example.homework1516.presentation.Repository
import com.example.homework1516.presentation.gifslist.MainActivityViewModel
import com.example.homework1516.SERVER_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//  presentation layer
val uiModule = module {
    viewModel { MainActivityViewModel() }
}

// data layer
val repoModule = module {
    single { RepositoryImpl(get()) } bind Repository::class
}

val networkModule = module {
    // ApiService
    single {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
