package com.example.jewerlyproducts.ui.dataclasses

import com.example.jewerlyproducts.data.products.ProductsEntity
import kotlin.math.cos

data class ProductsDataClass(
    val id:Int = 0,
    val name:String,
    val cost:Int,
    val sellValue:Int,
    val imageUri:String = ""
) {
    fun toEntity():ProductsEntity {
        return  ProductsEntity(
            id = id,
            name = name,
            cost = cost,
            sellValue = sellValue,
            imageUri = imageUri
        )
    }
}

