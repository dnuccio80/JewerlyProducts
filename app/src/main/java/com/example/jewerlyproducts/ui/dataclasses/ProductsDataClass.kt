package com.example.jewerlyproducts.ui.dataclasses

import com.example.jewerlyproducts.data.products.ProductsEntity

data class ProductsDataClass(
    val productName:String,
    val sellValue:Int,
    val imageUri:String = ""
) {
    fun toEntity():ProductsEntity {
        return  ProductsEntity(
            productName = productName,
            sellValue = sellValue,
            imageUri = imageUri
        )
    }
}

