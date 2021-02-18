package com.collegeapp.advertmarketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class Users(
        var name: String,

        @PrimaryKey
        var login: String,

        var password: String,

        var role: Int,

        @ColumnInfo(defaultValue = "0")
        var isLogin: Boolean
    )