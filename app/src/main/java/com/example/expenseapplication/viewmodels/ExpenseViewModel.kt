package com.example.expenseapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseapplication.data.entities.Expense
import com.example.expenseapplication.models.ExpenseModel
import kotlinx.coroutines.launch

class ExpenseViewModel(private val model: ExpenseModel): ViewModel() {
    val allExpenses: LiveData<List<Expense>> = model.allExpenses
    val totalExpenses: LiveData<Double> = model.totalExpenses

    val expenseName = MutableLiveData<String>()
    val expenseAmount = MutableLiveData<String>()
    val selectedCategoryIndex = MutableLiveData<Int>(0)

    private val categories = listOf("Food", "Transport", "Entertainment", "Other")

    fun insertExpense() {
        val name = expenseName.value.orEmpty()
        val amount = expenseAmount.value?.toDoubleOrNull() ?: 0.0
        val category = categories.getOrNull(selectedCategoryIndex.value ?: 0).orEmpty()

        if (name.isNotEmpty() && amount > 0) {
            val expense = Expense(name = name, amount = amount, category = category)
            viewModelScope.launch {
                model.insertExpense(expense)
            }
        }
    }

    fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            model.insertExpense(expense)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            model.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            model.deleteExpense(expense)
        }
    }

    fun clearAllExpenses() {
        viewModelScope.launch {
            model.deleteAllExpenses()
        }
    }

    fun clearForm() {
        expenseName.value = ""
        expenseAmount.value = ""
        selectedCategoryIndex.value = 0
    }
}