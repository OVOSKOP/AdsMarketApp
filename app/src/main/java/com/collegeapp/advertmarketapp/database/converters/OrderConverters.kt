package com.collegeapp.advertmarketapp.database.converters

import androidx.room.TypeConverter

class OrderConverters {

    @TypeConverter
    fun fromList(orders: List<String?>): String {
        return orders.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<String?> {
        return data.split(",")
    }

}