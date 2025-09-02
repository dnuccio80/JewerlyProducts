package com.example.jewerlyproducts.ui.screens.newproduct

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddNewProductViewModel @Inject constructor() : ViewModel() {

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _materialQuantity = MutableStateFlow("")
    val materialQuantity: StateFlow<String> = _materialQuantity

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog: StateFlow<Boolean> = _showMaterialDialog

    fun updateProductName(newValue: String) {
        _productName.value = newValue
    }

    fun updateMaterialQuantity(newValue: String) {
        _materialQuantity.value = newValue
    }

    fun updateShowMaterialDialog(newState: Boolean) {
        _showMaterialDialog.value = newState
    }

    fun clearMaterialStats() {
        _materialQuantity.value = ""
    }


}