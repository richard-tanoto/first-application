package com.richard.firstapplication.ui.secondscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondScreenViewModel : ViewModel() {

    private var _selectedName = MutableLiveData<String>()
    val selectedName: LiveData<String> = _selectedName

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    init {
        _selectedName.value = "No user is selected"
    }

    fun setName(mName: String) {
        _name.value = mName
    }

    fun setSelectedName(mSelectedName: String) {
        _selectedName.value = mSelectedName
    }

}