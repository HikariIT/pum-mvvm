package com.example.expenseapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseapplication.databinding.ActivityAddEditExpenseBinding
import com.example.expenseapplication.viewmodels.ExpenseViewModel
import com.example.expenseapplication.viewmodels.ExpenseViewModelFactory


class AddEditExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditExpenseBinding
    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((application as ExpenseApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditExpenseBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = expenseViewModel
        setContentView(binding.root)

        val spinner = binding.spinnerCategory
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, this.resources.getStringArray(R.array.expense_categories))
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expenseViewModel.selectedCategoryIndex.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                expenseViewModel.selectedCategoryIndex.value = 0
            }
        }

        // On save button click, insert the expense, clear the form and finish the activity, move to MainActivity
        binding.btnSave.setOnClickListener {
            expenseViewModel.insertExpense()
            expenseViewModel.clearForm()
            finish()
        }

        expenseViewModel.clearForm()
    }
}
