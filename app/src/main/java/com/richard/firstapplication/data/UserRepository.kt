package com.richard.firstapplication.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.richard.firstapplication.data.network.ApiService
import com.richard.firstapplication.data.response.UserItem

class UserRepository(private val apiService: ApiService) {

    fun getUsers() : LiveData<PagingData<UserItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }

}