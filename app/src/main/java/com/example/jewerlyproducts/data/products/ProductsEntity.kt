package com.example.jewerlyproducts.data.products

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import kotlin.math.cos

@Entity
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val cost:Int = 0,
    val sellValue:Int,
    val imageUri:String,
) {
    fun toDataClass(): ProductsDataClass {
        return ProductsDataClass(
            id = id,
            name = name,
            cost = cost,
            sellValue = sellValue,
            imageUri = imageUri
        )
    }
}
