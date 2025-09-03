package com.example.jewerlyproducts.data.materials

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass

@Entity
data class MaterialsEntity(
    @PrimaryKey (autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val unitPrice:Int,
    val pricePerPack:Int,
    val quantityPerPack:Int,
    val annotations:String
) {
    fun toDataClass():MaterialsDataClass {
        return MaterialsDataClass(
            id = id,
            name = name,
            unitPrice = unitPrice,
            pricePerPack = pricePerPack,
            quantityPerPack = quantityPerPack,
            annotations = annotations
        )
    }
}
