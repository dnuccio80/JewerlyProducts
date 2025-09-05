package com.example.jewerlyproducts.ui.screens.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repository: ProductRepository, materialsRepository: MaterialsRepository):ViewModel() {

    private val _productDetails = MutableStateFlow<ProductsDataClass?>(null)
    val productDetails:StateFlow<ProductsDataClass?> = _productDetails

    private val _productName = MutableStateFlow("")
    val productName:StateFlow<String> = _productName

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog:StateFlow<Boolean> = _showMaterialDialog

    private val _costValue = MutableStateFlow("")
    val costValue:StateFlow<String> = _costValue

    private val _productImageUri = MutableStateFlow("")
    val productImageUri: StateFlow<String> = _productImageUri

    private val _sellValue = MutableStateFlow("")
    val sellValue:StateFlow<String> = _sellValue

    private val _materialsList = materialsRepository.getAllMaterials().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val materialsList = _materialsList

    private val _materialQuantity = MutableStateFlow("")
    val materialQuantity: StateFlow<String> = _materialQuantity


    fun getProductById(productId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _productDetails.value = repository.getProductById(productId)
            }.await()
            _productName.value = _productDetails.value!!.name
            _costValue.value = _productDetails.value!!.cost.toString()
            _sellValue.value = _productDetails.value!!.sellValue.toString()
            _productImageUri.value = _productDetails.value!!.imageUri
        }
    }

    fun updateProductName(newValue: String) {
        _productName.value = newValue
    }

    fun updateShowMaterialDialog(show: Boolean) {
        _showMaterialDialog.value = show
    }

    fun updateSellValue(newValue: String) {
        _sellValue.value = newValue
    }

    fun updateMaterialQuantity(newValue: String) {
        _materialQuantity.value = newValue
    }

    fun clearMaterialStats() {
        _materialQuantity.value = ""
    }

    fun updateImageUri(newValue:String) {
        _productImageUri.value = newValue
    }

    fun updateProduct(product: ProductsDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(productId)
        }
    }


}