package com.example.jewerlyproducts.data.sells

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SellDao {

    @Insert
    suspend fun addSell(sell:SellEntity)

    @Query("SELECT * FROM sellentity")
    fun getAllSells(): Flow<List<SellEntity>>

    @Query("DELETE FROM SellEntity WHERE sellId = :sellId")
    suspend fun deleteSell(sellId:Int)

    @Update
    suspend fun updateSell(sell: SellEntity)

    @Query("DELETE FROM sellentity")
    suspend fun deleteAllSells()

}