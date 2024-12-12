package com.example.expenseapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expenseapplication.adapters.ExpenseAdapter
import com.example.expenseapplication.databinding.ActivityMainBinding
import com.example.expenseapplication.viewmodels.ExpenseViewModel
import com.example.expenseapplication.viewmodels.ExpenseViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((application as ExpenseApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using data binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = expenseViewModel
        setContentView(binding.root)

        // Initialize RecyclerView and Adapter
        val adapter = ExpenseAdapter(emptyList(), expenseViewModel)
        binding.recyclerViewExpenses.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewExpenses.adapter = adapter

        binding.btnClearExpenses.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(this.getString(R.string.alert_remove_title))
                .setMessage(this.getString(R.string.alert_remove_text))
                .setPositiveButton(R.string.global_yes) { _, _ ->
                    expenseViewModel.clearAllExpenses()
                }
                .setNegativeButton(R.string.global_no, null)
                .show()
        }

        binding.btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddEditExpenseActivity::class.java)
            startActivity(intent)
        }

        expenseViewModel.allExpenses.observe(this) { expenses ->
            Log.w("MainActivity", "All expenses have changed: $expenses")
            adapter.updateExpenses(expenses)
        }
    }
}