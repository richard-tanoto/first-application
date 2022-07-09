package com.richard.firstapplication.di

import com.richard.firstapplication.data.UserRepository
import com.richard.firstapplication.data.network.ApiConfig

object Injection {
    fun provideRepository() : UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }
}