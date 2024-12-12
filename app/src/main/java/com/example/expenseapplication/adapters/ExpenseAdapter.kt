package com.example.expenseapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapplication.R
import com.example.expenseapplication.data.entities.Expense
import com.example.expenseapplication.viewmodels.ExpenseViewModel
import java.util.Locale

class ExpenseAdapter(private var expenses: List<Expense>, private val viewModel: ExpenseViewModel) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvExpenseName)
        val categoryTextView: TextView = itemView.findViewById(R.id.tvExpenseCategory)
        val amountTextView: TextView = itemView.findViewById(R.id.tvExpenseAmount)
        val btnRemove: View = itemView.findViewById(R.id.btnRemoveExpense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.nameTextView.text = expense.name
        holder.categoryTextView.text = expense.category
        holder.amountTextView.text = String.format(Locale.US, "%.2f" + holder.itemView.context.getString(R.string.currency), expense.amount)
        holder.btnRemove.setOnClickListener {
            viewModel.deleteExpense(expense)
            updateExpenses(expenses.filter { it.id != expense.id })
        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun updateExpenses(newExpenses: List<Expense>) {
        val diffCallback = ExpenseDiffCallback(expenses, newExpenses)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        expenses = newExpenses
        diffResult.dispatchUpdatesTo(this)
    }
}

class ExpenseDiffCallback(
    private val oldList: List<Expense>,
    private val newList: List<Expense>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
