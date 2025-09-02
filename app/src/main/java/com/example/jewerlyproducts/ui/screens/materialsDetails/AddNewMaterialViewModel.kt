package com.example.jewerlyproducts.ui.screens.materialsDetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class AddNewMaterialViewModel @Inject constructor():ViewModel() {

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

}