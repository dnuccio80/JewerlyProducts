package com.example.jewerlyproducts.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.expenses.ExpensesRepository
import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.data.sells.SellRepository
import com.example.jewerlyproducts.ui.dataclasses.ExpenseDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import com.example.jewerlyproducts.ui.dataclasses.SellDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sellRepository: SellRepository,
    private val productRepository: ProductRepository,
    private val expenseRepository: ExpensesRepository,
) : ViewModel() {

    private val _showAddSellDialog = MutableStateFlow(false)
    val showAddSellDialog: StateFlow<Boolean> = _showAddSellDialog

    private val _showAddExpensesDialog = MutableStateFlow(false)
    val showAddExpensesDialog: StateFlow<Boolean> = _showAddExpensesDialog

    private val _quantitySell = MutableStateFlow("")
    val quantitySell: StateFlow<String> = _quantitySell

    private val _expensesDescription = MutableStateFlow("")
    val expensesDescription: StateFlow<String> = _expensesDescription

    private val _expensesPrice = MutableStateFlow("")
    val expensesPrice: StateFlow<String> = _expensesPrice

    private val _sellList = sellRepository.getAllSells()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val sellList: StateFlow<List<SellDataClass>> = _sellList

    private val _expensesList = expenseRepository.getAllExpenses().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val expensesList:StateFlow<List<ExpenseDataClass>> = _expensesList

    private val _productList = productRepository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val productList = _productList

    private val _totalSells = MutableStateFlow(0)
    val totalSells:StateFlow<Int> = _totalSells

    private val _totalExpenses = MutableStateFlow(0)
    val totalExpenses:StateFlow<Int> = _totalExpenses

    fun updateShowSellDialog(show: Boolean) {
        _showAddSellDialog.value = show
    }

    fun updateShowAddExpensesDialog(show: Boolean) {
        _showAddExpensesDialog.value = show
    }

    fun updateQuantitySell(newValue: String) {
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

    fun addSell(product: ProductsDataClass, quantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sellRepository.addSell(
                    sell = SellDataClass(
                        product = product,
                        quantity = quantity
                    )
                )
            }
        }
    }

    fun addExpense(description:String, cost:Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                expenseRepository.addExpense(
                    expense = ExpenseDataClass(
                        description = description,
                        value = cost
                    )
                )
            }
        }
    }

    fun clearBalance() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                expenseRepository.deleteAllExpenses()
                sellRepository.deleteAllSells()
            }
        }
    }

    fun updateTotalSells() {
        _totalSells.value = 0

        _sellList.value.forEach { sell ->
            _totalSells.value += getTotals(sell.product.sellValue, sell.quantity)
        }
    }

    fun updateTotalExpenses() {
        _totalExpenses.value = 0

        _expensesList.value.forEach { expense ->
            _totalExpenses.value += expense.value
        }
    }

    private fun getTotals(value:Int, quantity: Int):Int = value * quantity


}