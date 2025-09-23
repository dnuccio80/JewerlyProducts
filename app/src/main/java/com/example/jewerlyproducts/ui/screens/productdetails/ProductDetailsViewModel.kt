package com.example.jewerlyproducts.ui.screens.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.data.relations.MaterialWithQuantity
import com.example.jewerlyproducts.domain.UpdateProductUseCase
import com.example.jewerlyproducts.ui.dataclasses.MaterialInProductWithQuantityDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    materialsRepository: MaterialsRepository,
    private val updateProductUseCase: UpdateProductUseCase,
) : ViewModel() {

    private val _productDetails = MutableStateFlow<ProductsDataClass?>(null)
    val productDetails: StateFlow<ProductsDataClass?> = _productDetails

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog: StateFlow<Boolean> = _showMaterialDialog

    private val _costValue = MutableStateFlow(0)
    val costValue: StateFlow<Int> = _costValue

    private val _productImageUri = MutableStateFlow("")
    val productImageUri: StateFlow<String> = _productImageUri

    private val _sellValue = MutableStateFlow("")
    val sellValue: StateFlow<String> = _sellValue

    private val _materialsList = materialsRepository.getAllMaterials()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val materialsList = _materialsList

    private val _materialQuantity = MutableStateFlow("")
    val materialQuantity: StateFlow<String> = _materialQuantity

    private val _materialsInProduct =
        MutableStateFlow<List<MaterialInProductWithQuantityDataClass>>(emptyList())
    val materialsInProduct = _materialsInProduct

    fun getProductByName(productName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _productDetails.value = repository.getProductById(productName)
            }.await()
            _productName.value = _productDetails.value!!.productName
            _sellValue.value = _productDetails.value!!.sellValue.toString()
            _productImageUri.value = _productDetails.value!!.imageUri
            _materialsInProduct.value = repository.getMaterialsWithQuantity(productName)
            updateCostValue()
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

    fun updateImageUri(newValue: String) {
        _productImageUri.value = newValue
    }

    fun updateProduct(product: ProductsDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            updateProductUseCase(
                product = product,
                materialList = _materialsInProduct.value
            )
        }
    }

    fun deleteProduct(productName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(productName)
        }
    }

    fun isSellValueGreaterThanCostValue(): Boolean {
        return _sellValue.value.toInt() > _costValue.value.toInt()
    }

    private fun updateCostValue() {
        _costValue.value = 0
        _materialsInProduct.value.forEach { material ->
            _costValue.value += getMaterialCost(
                getUnitPrice(
                    material.material.quantityPerPack,
                    material.material.pricePerPack
                ), material.quantity
            )
        }
    }

    fun addMaterialInProduct(material: MaterialInProductWithQuantityDataClass) {

        val currentList = _materialsInProduct.value
        val existingIndex =
            _materialsInProduct.value.indexOfFirst { it.material.materialName == material.material.materialName }

        if (existingIndex != -1) {
            // The product exist in list so we change values
            val newList = currentList.toMutableList().apply {
                this[existingIndex] = material.copy(
                    quantity = this[existingIndex].quantity + material.quantity,
                )
            }
            _materialsInProduct.value = newList
            updateCostValue()
        } else {
            _materialsInProduct.value += material
            updateCostValue()
        }
    }

    fun updateMaterialInProduct(material: MaterialInProductWithQuantityDataClass) {
        val materialIndex = _materialsInProduct.value.indexOfFirst { it.material.materialName == material.material.materialName }
        val newList = _materialsInProduct.value.toMutableList().apply {
            this[materialIndex] = material
        }

        _materialsInProduct.value = newList
        updateCostValue()
    }

    fun deleteMaterialInProduct(materialName: String) {
        val materialIndex =
            _materialsInProduct.value.indexOfFirst { it.material.materialName == materialName }

        _materialsInProduct.value -= _materialsInProduct.value[materialIndex]
        updateCostValue()
    }

}

fun getMaterialCost(unitPrice: Int, quantity: Int): Int = unitPrice * quantity
fun getUnitPrice(unitsPerPack: Int, packValue: Int): Int =
    ceil(packValue.toDouble() / unitsPerPack).toInt()
