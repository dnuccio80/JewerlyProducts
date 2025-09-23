package com.example.jewerlyproducts.ui.routes

sealed class Routes(val routes:String) {
    data object Home:Routes("home")
    data object ProductList:Routes("ProductList")
    data object AddNewProduct:Routes("AddNewProduct")
    data object MaterialList:Routes("MaterialList")
    data object MaterialDetail:Routes("MaterialDetail/{materialName}") {
        fun createRoute(materialName:String) = "MaterialDetail/$materialName"
    }
    data object ProductDetails:Routes("ProductDetails/{productName}") {
        fun createRoute(productName:String) = "ProductDetails/$productName"
    }
    data object AddNewMaterial:Routes("AddNewMaterial")
}
