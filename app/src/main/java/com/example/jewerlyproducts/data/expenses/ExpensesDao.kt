package com.example.jewerlyproducts.data.expenses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM ExpensesEntity")
    fun getAllExpenses(): Flow<List<ExpensesEntity>>

    @Insert
    suspend fun addExpense(expense: ExpensesEntity)

    @Query("DELETE FROM expensesentity WHERE expenseId = :expenseId")
    suspend fun deleteExpenseFromId(expenseId:Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expense: ExpensesEntity)

    @Query("DELETE FROM ExpensesEntity")
    suspend fun deleteAllExpenses()

}