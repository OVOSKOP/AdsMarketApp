package com.collegeapp.advertmarketapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.collegeapp.advertmarketapp.database.entity.Users

@Dao
interface UsersDAO {
    @Query("SELECT * FROM users")
    suspend fun getList() : List<Users>

    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getByLogin(login: String) : Users

    @Query("SELECT * FROM users WHERE isLogin = 1")
    suspend fun loginExist() : Users

    @Insert
    suspend fun insert(user: Users)

    @Update
    suspend fun update(user: Users)

    @Delete
    suspend fun delete(user: Users)
}