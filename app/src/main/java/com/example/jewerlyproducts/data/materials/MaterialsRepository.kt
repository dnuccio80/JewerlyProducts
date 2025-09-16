package com.example.jewerlyproducts.data.materials

import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MaterialsRepository @Inject constructor(private val materialsDao: MaterialsDao) {


    fun getAllMaterials(): Flow<List<MaterialsDataClass>> = materialsDao.getAllMaterials().map { list ->
        list.sortedBy {
            it.materialName.lowercase()
        }.map {entity ->
            entity.toDataClass()
        }
    }

    fun getMaterialByQuery(query:String): Flow<List<MaterialsDataClass>> = materialsDao.getWorkByDescription(query).map {
        it.map { entity ->
            entity.toDataClass()
        }
    }

    fun addMaterial(newMaterial: MaterialsDataClass) {
        materialsDao.addMaterial(newMaterial.toEntity())
    }

    suspend fun getMaterialByName(materialName:String): MaterialsDataClass {
        return materialsDao.getMaterialByName(materialName).toDataClass()
    }

    fun deleteMaterial(materialName:String) {
        materialsDao.deleteMaterialByName(materialName)
    }

    suspend fun updateMaterial(material: MaterialsDataClass) {
        materialsDao.updateMaterial(material.toEntity())
    }


}