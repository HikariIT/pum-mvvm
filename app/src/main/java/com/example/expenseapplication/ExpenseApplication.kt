package com.example.expenseapplication

import android.app.Application
import com.example.expenseapplication.data.database.AppDatabase
import com.example.expenseapplication.models.ExpenseModel

class ExpenseApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ExpenseModel(database.expenseDao()) }
}