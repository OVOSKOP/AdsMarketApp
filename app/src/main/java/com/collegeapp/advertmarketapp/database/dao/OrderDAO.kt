package com.collegeapp.advertmarketapp.database.dao

import androidx.room.*
import com.collegeapp.advertmarketapp.database.entity.Order

@Dao
interface OrderDAO {
    @Query("SELECT * FROM orders")
    suspend fun getList() : List<Order>

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getById(id: Int) : Order

    @Query("SELECT * FROM orders WHERE status = :status")
    suspend fun getByStatus(status: Int) : List<Order>

    @Insert
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)
}