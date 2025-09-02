package com.example.jewerlyproducts.ui.screens.home

import android.text.BoringLayout
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {

    private val _showAddSellDialog = MutableStateFlow(false)
    val showAddSellDialog:StateFlow<Boolean> = _showAddSellDialog

    private val _showAddExpensesDialog = MutableStateFlow(false)
    val showAddExpensesDialog:StateFlow<Boolean> = _showAddExpensesDialog

    private val _quantitySell = MutableStateFlow("")
    val quantitySell:StateFlow<String> = _quantitySell

    private val _expensesDescription = MutableStateFlow("")
    val expensesDescription:StateFlow<String> = _expensesDescription

    private val _expensesPrice = MutableStateFlow("")
    val expensesPrice:StateFlow<String> = _expensesPrice

    fun updateShowSellDialog(show:Boolean) {
        _showAddSellDialog.value = show
    }
    fun updateShowAddExpensesDialog(show:Boolean) {
        _showAddExpensesDialog.value = show
    }

    fun updateQuantitySell(newValue:String) {
        _quantitySell.value = newValue
    }

    fun updateExpensesDescription(newValue: String) {
        _expensesDescription.value = newValue
    }

    fun updateExpensesPrice(newValue: String) {
        _expensesPrice.value = newValue
    }

    fun clearSellStats() {
        _quantitySell.value = ""
    }

    fun clearExpensesStats() {
        _expensesPrice.value = ""
        _expensesDescription.value = ""
    }

}