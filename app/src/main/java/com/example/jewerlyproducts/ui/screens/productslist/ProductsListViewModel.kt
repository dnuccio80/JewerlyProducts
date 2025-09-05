package com.example.jewerlyproducts.ui.screens.productslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(private val repository: ProductRepository):ViewModel() {

    private val _productsList = repository.getAllProducts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val productList: StateFlow<List<ProductsDataClass>> = _productsList



}