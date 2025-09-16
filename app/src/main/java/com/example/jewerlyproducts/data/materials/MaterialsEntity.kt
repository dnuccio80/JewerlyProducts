package com.example.jewerlyproducts.data.materials

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass

@Entity
data class MaterialsEntity(
    @PrimaryKey (autoGenerate = false)
    val materialName:String,
    val pricePerPack:Int,
    val quantityPerPack:Int,
    val annotations:String
) {
    fun toDataClass():MaterialsDataClass {
        return MaterialsDataClass(
            materialName = materialName,
            pricePerPack = pricePerPack,
            quantityPerPack = quantityPerPack,
            annotations = annotations
        )
    }
}
