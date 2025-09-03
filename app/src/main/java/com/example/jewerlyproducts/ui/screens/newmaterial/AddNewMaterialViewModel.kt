package com.example.jewerlyproducts.ui.screens.newmaterial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass
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
class AddNewMaterialViewModel @Inject constructor(private val repository: MaterialsRepository):ViewModel() {

    private val _name = MutableStateFlow("")
    val name:StateFlow<String> =_name

    private val _price = MutableStateFlow<Int?>(null)
    val price:StateFlow<Int?> = _price

    private val _quantityPerPack = MutableStateFlow<Int?>(null)
    val quantityPerPack:StateFlow<Int?> = _quantityPerPack

    private val _individualPrice = MutableStateFlow<Int?>(0)
    val individualPrice:StateFlow<Int?> = _individualPrice

    private val _annotations = MutableStateFlow("")
    val annotations:StateFlow<String> = _annotations

    fun updateName(newValue:String) {
        _name.value = newValue
    }

    fun updatePrice(newValue:String) {
        _price.value = newValue.toIntOrNull()
        updateIndividualPrice()
    }

    fun updateQuantityPerPack(newValue:String) {
        _quantityPerPack.value = newValue.toIntOrNull()
        updateIndividualPrice()
    }

    fun updateAnnotations(newValue:String) {
        _annotations.value = newValue
    }

    private fun updateIndividualPrice() {
        if(_price.value == 0 || _price.value == null || _quantityPerPack.value == 0 || _quantityPerPack.value == null) {
            _individualPrice.value = null
            return
        }
        _individualPrice.value = ceil(_price.value!!.toDouble() / _quantityPerPack.value!!).toInt()
    }

    fun addNewMaterial(newMaterial:MaterialsDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMaterial(newMaterial)
        }
    }

}