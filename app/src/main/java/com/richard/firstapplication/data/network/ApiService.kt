package com.richard.firstapplication.data.network

import com.richard.firstapplication.data.response.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int,
    ): Response
}