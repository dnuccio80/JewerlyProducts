package com.example.jewerlyproducts.ui.dataclasses

import com.example.jewerlyproducts.data.expenses.ExpensesEntity

data class ExpenseDataClass(
    val expenseId: Int = 0,
    val description: String,
    val value: Int
) {
    fun toEntity(): ExpensesEntity {
        return ExpensesEntity(
            expenseId = expenseId,
            description = description,
            value = value
        )
    }
}