package com.collegeapp.advertmarketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
class Client(
    var name: String,
    var email: String,
    var phone: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}