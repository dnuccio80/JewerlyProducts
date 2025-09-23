package com.example.jewerlyproducts.data.sells

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jewerlyproducts.data.products.ProductsEntity
import com.example.jewerlyproducts.ui.dataclasses.SellDataClass

@Entity
data class SellEntity(
    @PrimaryKey(autoGenerate = true)
    val sellId:Int = 0,
    @Embedded val product:ProductsEntity,
    val quantity:Int
) {
    fun toDataClass():SellDataClass {
        return SellDataClass(
            sellId = sellId,
            product = product.toDataClass(),
            quantity = quantity
        )
    }
}
