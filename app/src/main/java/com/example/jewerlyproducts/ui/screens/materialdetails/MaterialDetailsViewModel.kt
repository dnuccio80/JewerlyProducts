package com.example.jewerlyproducts.ui.screens.materialdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class MaterialDetailsViewModel @Inject constructor(private val repository: MaterialsRepository):ViewModel() {

    private val _materialDetails = MutableStateFlow<MaterialsDataClass?>(null)
    val materialDetails:StateFlow<MaterialsDataClass?> = _materialDetails

    private val _materialName = MutableStateFlow("")
    val materialName:StateFlow<String> = _materialName

    private val _materialPrice = MutableStateFlow<Int?>(null)
    val materialPrice:StateFlow<Int?> = _materialPrice

    private val _quantityPerPack = MutableStateFlow<Int?>(null)
    val quantityPerPack:StateFlow<Int?> = _quantityPerPack

    private val _annotations = MutableStateFlow("")
    val annotations:StateFlow<String> = _annotations

    private val _individualPrice = MutableStateFlow<Int?>(null)
    val individualPrice:StateFlow<Int?> = _individualPrice

    fun getMaterialByName(materialName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _materialDetails.value = repository.getMaterialByName(materialName)
            }.await()
            _materialName.value = _materialDetails.value?.materialName.orEmpty()
            _materialPrice.value = _materialDetails.value?.pricePerPack
            _quantityPerPack.value = _materialDetails.value?.quantityPerPack
            _annotations.value = _materialDetails.value?.annotations.orEmpty()
            updateIndividualPrice()
        }
    }

    fun updateName(newValue:String) {
        _materialName.value = newValue
    }

    fun updatePrice(newValue:String) {
        _materialPrice.value = newValue.toIntOrNull()
        updateIndividualPrice()
    }

    fun updateQuantityPerPack(newValue: String) {
        _quantityPerPack.value = newValue.toIntOrNull()
        updateIndividualPrice()
    }
    fun updateAnnotations(newValue: String) {
        _annotations.value = newValue
    }

    private fun updateIndividualPrice() {
        if(_materialPrice.value == 0 || _materialPrice.value == null || _quantityPerPack.value == 0 || _quantityPerPack.value == null) {
            _individualPrice.value = null
            return
        }
        _individualPrice.value = ceil(_materialPrice.value!!.toDouble() / _quantityPerPack.value!!).toInt()
    }

    fun updateMaterial(materialName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMaterial(
                MaterialsDataClass(
                    materialName = this@MaterialDetailsViewModel.materialName.value,
                    quantityPerPack = quantityPerPack.value!!,
                    pricePerPack = materialPrice.value!!,
                    annotations = annotations.value
                )
            )
        }
    }

    fun deleteMaterialById(materialName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMaterial(materialName)
        }
    }


}