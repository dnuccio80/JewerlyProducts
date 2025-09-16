package com.example.jewerlyproducts.data.products

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass

@Entity
data class ProductsEntity(
    @PrimaryKey(autoGenerate = false)
    val productName:String,
    val sellValue:Int,
    val imageUri:String,
) {
    fun toDataClass(): ProductsDataClass {
        return ProductsDataClass(
            productName = productName,
            sellValue = sellValue,
            imageUri = imageUri
        )
    }
}
