package com.richard.firstapplication.ui.firstscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstScreenViewModel : ViewModel() {

    private var _isPalindrome = MutableLiveData<Boolean>()
    val isPalindrome: LiveData<Boolean> = _isPalindrome

    fun checkPalindrome(query: String) {
        val string = query.filter { !it.isWhitespace() }
        _isPalindrome.value = string.equals(string.reversed(), true)
    }

}