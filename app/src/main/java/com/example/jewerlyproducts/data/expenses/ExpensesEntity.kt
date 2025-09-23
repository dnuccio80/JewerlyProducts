package com.example.jewerlyproducts.data.expenses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jewerlyproducts.ui.dataclasses.ExpenseDataClass

@Entity
data class ExpensesEntity(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int,
    val description: String,
    val value: Int
) {
    fun toDataClass(): ExpenseDataClass
    {
        return ExpenseDataClass(
            expenseId = expenseId,
            description = description,
            value = value
        )
    }
}
