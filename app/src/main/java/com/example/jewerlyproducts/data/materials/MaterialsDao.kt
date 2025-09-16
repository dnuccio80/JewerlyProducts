package com.example.jewerlyproducts.data.materials

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialsDao {

    @Query("SELECT * FROM MATERIALSENTITY")
    fun getAllMaterials(): Flow<List<MaterialsEntity>>

    @Query("SELECT * FROM MaterialsEntity WHERE materialName LIKE '%' || :query || '%'")
    fun getWorkByDescription(query: String): Flow<List<MaterialsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMaterial(materialsEntity: MaterialsEntity)

    @Query("SELECT * FROM MATERIALSENTITY WHERE materialName = :materialName")
    suspend fun getMaterialByName(materialName:String): MaterialsEntity

    @Query("DELETE FROM MATERIALSENTITY WHERE materialName = :materialName")
    fun deleteMaterialByName(materialName: String)

    @Update
    suspend fun updateMaterial(material: MaterialsEntity)
    
}