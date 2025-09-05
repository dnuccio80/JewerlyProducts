package com.example.jewerlyproducts.ui.screens.newproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewProductViewModel @Inject constructor(private val materialsRepository: MaterialsRepository, private val productRepository: ProductRepository) : ViewModel() {

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _materialQuantity = MutableStateFlow("")
    val materialQuantity: StateFlow<String> = _materialQuantity

    private val _productImageUri = MutableStateFlow("")
    val productImageUri: StateFlow<String> = _productImageUri

    private val _costValue = MutableStateFlow<Int?>(2000)
    val costValue: StateFlow<Int?> = _costValue

    private val _sellValue = MutableStateFlow<Int?>(null)
    val sellValue: StateFlow<Int?> = _sellValue

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog: StateFlow<Boolean> = _showMaterialDialog

    private val _materialsList = materialsRepository.getAllMaterials().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val materialsList = _materialsList

    fun updateProductName(newValue: String) {
        _productName.value = newValue
    }

    fun updateMaterialQuantity(newValue: String) {
        _materialQuantity.value = newValue
    }

    fun updateShowMaterialDialog(newState: Boolean) {
        _showMaterialDialog.value = newState
    }

    fun updateSellValue(newValue:String) {
        _sellValue.value = newValue.toIntOrNull()
    }

    fun clearMaterialStats() {
        _materialQuantity.value = ""
    }

    fun addNewProduct(product:ProductsDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.addNewProduct(product)
        }
    }
    fun updateImageUri(newValue:String) {
        _productImageUri.value = newValue
    }


}