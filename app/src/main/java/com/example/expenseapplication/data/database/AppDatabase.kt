package com.example.expenseapplication.data.database

import com.example.expenseapplication.data.entities.Expense
import com.example.expenseapplication.data.dao.ExpenseDao
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import android.content.Context

@Database(entities = [Expense::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private const val DATABASE_NAME = "words.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME,
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}