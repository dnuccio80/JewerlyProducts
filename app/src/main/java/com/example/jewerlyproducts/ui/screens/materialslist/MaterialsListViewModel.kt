package com.example.jewerlyproducts.ui.screens.materialslist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MaterialsListViewModel @Inject constructor():ViewModel() {

    private val _searchName = MutableStateFlow("")
    val searchName:StateFlow<String> = _searchName

    fun updateSearchName(newValue: String) {
        _searchName.value = newValue
    }


}