package com.example.jewerlyproducts.ui.dataclasses

import com.example.jewerlyproducts.data.materials.MaterialsEntity

data class MaterialsDataClass(
    val id:Int = 0,
    val name:String,
    val unitPrice:Int,
    val quantityPerPack:Int,
    val pricePerPack:Int,
    val annotations:String
)

fun MaterialsDataClass.toEntity():MaterialsEntity {
    return MaterialsEntity(
        id = id,
        name = name,
        unitPrice = unitPrice,
        quantityPerPack = quantityPerPack,
        pricePerPack = pricePerPack,
        annotations = annotations
    )
}
