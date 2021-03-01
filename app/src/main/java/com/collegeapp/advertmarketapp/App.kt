package com.collegeapp.advertmarketapp

import android.app.Application
import android.util.Log
import androidx.room.CoroutinesRoom
import androidx.room.Room
import com.collegeapp.advertmarketapp.database.AppDatabase
import com.collegeapp.advertmarketapp.database.Migrations
import com.collegeapp.advertmarketapp.database.entity.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {
    private lateinit var database: AppDatabase
    var currentUser: Users? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("APP CREATE", "CREATE")
        database = Room.databaseBuilder(this, AppDatabase::class.java, "db_ads")
                .addMigrations(Migrations.MIGRATION_5_6)
                .build()

        CoroutineScope(Dispatchers.IO).launch {
            currentUser = database.usersDAO().loginExist()
        }
    }


    fun getDatabase() : AppDatabase {
        return database
    }
}