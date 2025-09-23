package com.example.jewerlyproducts.ui.dataclasses

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
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

val ProductSaver: Saver<MutableState<ProductsDataClass>, List<Any>> =
    Saver(
        save = { state ->
            listOf(
                state.value.productName,
                state.value.sellValue,
                state.value.imageUri,
            )
        },
        restore = { list ->
            mutableStateOf(
                ProductsDataClass(
                    productName = list[0] as String,
                    sellValue = list[1] as Int,
                    imageUri = list[2] as String,
                )
            )
        }
    )