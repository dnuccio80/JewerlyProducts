package com.example.jewerlyproducts.data.expenses

import com.example.jewerlyproducts.ui.dataclasses.ExpenseDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpensesRepository @Inject constructor(private val dao:ExpensesDao) {

    fun getAllExpenses(): Flow<List<ExpenseDataClass>> = dao.getAllExpenses().map { list ->
        list.map { item ->
            item.toDataClass()
        }
    }

    suspend fun addExpense(expense: ExpenseDataClass) = dao.addExpense(expense.toEntity())

    suspend fun deleteExpenseFromId(expenseId:Int) = dao.deleteExpenseFromId(expenseId)

    suspend fun updateExpense(expense: ExpensesEntity) = dao.updateExpense(expense)

    suspend fun deleteAllExpenses() = dao.deleteAllExpenses()

}