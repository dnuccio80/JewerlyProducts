package com.example.jewerlyproducts.data.sells

import androidx.room.Delete
import androidx.room.Update
import com.example.jewerlyproducts.ui.dataclasses.SellDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SellRepository @Inject constructor(private val dao: SellDao) {

    suspend fun addSell(sell: SellDataClass) = dao.addSell(sell.toEntity())

    fun getAllSells(): Flow<List<SellDataClass>> = dao.getAllSells().map { list ->
        list.map { entity ->
            entity.toDataClass()
        }
    }

    suspend fun deleteSell(sellId:Int) = dao.deleteSell(sellId)

    suspend fun updateSell(sell: SellDataClass) = dao.updateSell(sell.toEntity())

    suspend fun deleteAllSells() = dao.deleteAllSells()
}