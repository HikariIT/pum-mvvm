package com.example.expenseapplication.models

import androidx.lifecycle.LiveData
import com.example.expenseapplication.data.dao.ExpenseDao
import com.example.expenseapplication.data.entities.Expense

class ExpenseModel(private val expenseDao: ExpenseDao) {
    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    suspend fun deleteAllExpenses() {
        expenseDao.deleteAllExpenses()
    }
}