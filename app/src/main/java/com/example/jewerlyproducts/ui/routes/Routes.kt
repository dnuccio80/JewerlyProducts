package com.example.jewerlyproducts.ui.routes

sealed class Routes(val routes:String) {
    data object Home:Routes("home")
    data object ProductList:Routes("ProductList")
    data object ProductDetail:Routes("ProductDetails")
    data object AddNewProduct:Routes("AddNewProduct")
    data object MaterialList:Routes("MaterialList")
    data object MaterialDetail:Routes("MaterialDetail")
    data object AddNewMaterial:Routes("AddNewMaterial")
}
