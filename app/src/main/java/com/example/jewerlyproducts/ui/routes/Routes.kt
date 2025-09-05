package com.example.jewerlyproducts.ui.routes

sealed class Routes(val routes:String) {
    data object Home:Routes("home")
    data object ProductList:Routes("ProductList")
    data object AddNewProduct:Routes("AddNewProduct")
    data object MaterialList:Routes("MaterialList")
    data object MaterialDetail:Routes("MaterialDetail/{materialId}") {
        fun createRoute(materialId:Int) = "MaterialDetail/$materialId"
    }
    data object ProductDetails:Routes("ProductDetails/{productId}") {
        fun createRoute(productId:Int) = "ProductDetails/$productId"
    }
    data object AddNewMaterial:Routes("AddNewMaterial")
}
