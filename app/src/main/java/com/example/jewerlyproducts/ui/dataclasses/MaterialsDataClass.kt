package com.example.jewerlyproducts.ui.dataclasses

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.example.jewerlyproducts.data.materials.MaterialsEntity

data class MaterialsDataClass(
    val materialName:String,
    val quantityPerPack:Int,
    val pricePerPack:Int,
    val annotations:String
) {
    fun toEntity():MaterialsEntity {
        return MaterialsEntity(
            materialName = materialName,
            quantityPerPack = quantityPerPack,
            pricePerPack = pricePerPack,
            annotations = annotations
        )
    }
}

val MaterialsSaver: Saver<MutableState<MaterialsDataClass>, List<Any>> =
    Saver(
        save = { state ->
            listOf(
                state.value.materialName,
                state.value.quantityPerPack,
                state.value.pricePerPack,
                state.value.annotations
            )
        },
        restore = { list ->
            mutableStateOf(
                MaterialsDataClass(
                    materialName = list[0] as String,
                    quantityPerPack = list[1] as Int,
                    pricePerPack = list[2] as Int,
                    annotations = list[3] as String
                )
            )
        }
    )

