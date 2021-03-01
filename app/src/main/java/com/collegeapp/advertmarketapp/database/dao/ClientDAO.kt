package com.collegeapp.advertmarketapp.database.dao

import androidx.room.*
import com.collegeapp.advertmarketapp.database.entity.Client
import com.collegeapp.advertmarketapp.database.entity.Order

@Dao
interface ClientDAO {

    @Query("SELECT * FROM clients")
    suspend fun getList() : List<Client>

    @Query("SELECT * FROM clients WHERE id = :id")
    suspend fun getById(id: Int) : Client

    @Insert
    suspend fun insert(client: Client) : Long

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)

}