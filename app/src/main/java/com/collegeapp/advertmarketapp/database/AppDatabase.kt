package com.collegeapp.advertmarketapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.collegeapp.advertmarketapp.database.dao.ClientDAO
import com.collegeapp.advertmarketapp.database.dao.OrderDAO
import com.collegeapp.advertmarketapp.database.dao.UsersDAO
import com.collegeapp.advertmarketapp.database.entity.Client
import com.collegeapp.advertmarketapp.database.entity.Order
import com.collegeapp.advertmarketapp.database.entity.Users

@Database(entities = [Users::class, Order::class, Client::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDAO() : UsersDAO
    abstract fun orderDAO() : OrderDAO
    abstract fun clientDAO() : ClientDAO
}