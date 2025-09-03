package com.example.jewerlyproducts.data.materials

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialsDao {

    @Query("SELECT * FROM MATERIALSENTITY")
    fun getAllMaterials(): Flow<List<MaterialsEntity>>

    @Query("SELECT * FROM MaterialsEntity WHERE name LIKE '%' || :query || '%'")
    fun getWorkByDescription(query: String): Flow<List<MaterialsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMaterial(materialsEntity: MaterialsEntity)

    @Query("SELECT * FROM MATERIALSENTITY WHERE id = :materialId")
    suspend fun getMaterialById(materialId:Int): MaterialsEntity

    @Query("DELETE FROM MATERIALSENTITY WHERE id = :materialId")
    fun deleteMaterialById(materialId: Int)

    @Update
    suspend fun updateMaterial(material: MaterialsEntity)
    
}