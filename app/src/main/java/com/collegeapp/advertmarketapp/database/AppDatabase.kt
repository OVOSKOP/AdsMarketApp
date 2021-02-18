package com.collegeapp.advertmarketapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.collegeapp.advertmarketapp.database.dao.UsersDAO
import com.collegeapp.advertmarketapp.database.entity.Users

@Database(entities = [Users::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDAO() : UsersDAO
}