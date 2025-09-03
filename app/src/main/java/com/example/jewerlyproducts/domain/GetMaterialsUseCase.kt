package com.example.jewerlyproducts.domain

import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaterialsUseCase @Inject constructor(private val repository: MaterialsRepository) {
    operator fun invoke(query: String) =
        if(query.isBlank()){
            repository.getAllMaterials()
        } else {
            repository.getMaterialByQuery(query)
        }
}