package com.example.expenseapplication.data.dao

import com.example.expenseapplication.data.entities.Expense
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY id DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()
}
