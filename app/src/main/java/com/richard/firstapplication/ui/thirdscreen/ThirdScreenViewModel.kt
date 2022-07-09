package com.richard.firstapplication.ui.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.richard.firstapplication.data.UserRepository
import com.richard.firstapplication.data.response.UserItem
import com.richard.firstapplication.di.Injection

class ThirdScreenViewModel(userRepository: UserRepository) : ViewModel() {

    val users: LiveData<PagingData<UserItem>> = userRepository.getUsers().cachedIn(viewModelScope)

}

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThirdScreenViewModel(Injection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}