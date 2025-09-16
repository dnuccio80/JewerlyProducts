package com.example.jewerlyproducts.ui.screens.newproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.domain.AddNewProductUseCase
import com.example.jewerlyproducts.ui.dataclasses.MaterialInProductWithQuantityDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class AddNewProductViewModel @Inject constructor(
    private val materialsRepository: MaterialsRepository,
    private val productRepository: ProductRepository,
    private val addNewProductUseCase: AddNewProductUseCase,
) : ViewModel() {

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _materialQuantity = MutableStateFlow("")
    val materialQuantity: StateFlow<String> = _materialQuantity

    private val _productImageUri = MutableStateFlow("")
    val productImageUri: StateFlow<String> = _productImageUri

    private val _costValue = MutableStateFlow(0)
    val costValue: StateFlow<Int> = _costValue

    private val _sellValue = MutableStateFlow<Int?>(null)
    val sellValue: StateFlow<Int?> = _sellValue

    private val _showMaterialDialog = MutableStateFlow(false)
    val showMaterialDialog: StateFlow<Boolean> = _showMaterialDialog

    private val _materialsInProduct =
        MutableStateFlow<List<MaterialInProductWithQuantityDataClass>>(emptyList())
    val materialsInProduct: StateFlow<List<MaterialInProductWithQuantityDataClass>> =
        _materialsInProduct

    private val _materialsList = materialsRepository.getAllMaterials()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
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

    fun updateSellValue(newValue: String) {
        _sellValue.value = newValue.toIntOrNull()
    }

    fun clearMaterialStats() {
        _materialQuantity.value = ""
    }

    fun addNewProduct(product: ProductsDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewProductUseCase(product, _materialsInProduct.value)
        }
    }

    fun updateImageUri(newValue: String) {
        _productImageUri.value = newValue
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

    private fun updateCostValue() {
        _costValue.value = 0
        _materialsInProduct.value.forEach { material ->
           _costValue.value += getMaterialCost(material.quantity, getUnitPrice(
                pricePerPack = material.material.pricePerPack,
                quantityPerPack = material.material.quantityPerPack
            ))
        }
    }

    fun isSellValueGreaterThanCostValue():Boolean {
        return _sellValue.value!!.toInt() > _costValue.value
    }

    private fun getMaterialCost(quantity: Int, unitPrice: Int): Int = quantity * unitPrice

    private fun getUnitPrice(pricePerPack: Int, quantityPerPack: Int): Int = ceil(pricePerPack.toDouble() / quantityPerPack).toInt()






}