package com.collegeapp.advertmarketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.collegeapp.advertmarketapp.database.converters.OrderConverters
import com.collegeapp.advertmarketapp.utils.StatusCode

@Entity(tableName = "orders")
@TypeConverters(OrderConverters::class)
class Order(
        var title: String,
        @ColumnInfo(name = "client_id")
    var clientId: Long,
        var type: Int,
        @ColumnInfo(name = "area_description")
    var areaDescription: String,
        @ColumnInfo(name = "client_description")
    var clientDescription: String,
        @ColumnInfo(name = "date_added")
    var date: Long,
        var status: Int = StatusCode.CLIENT,
        var color: String? = null,
        var plan: String? = null,
        @ColumnInfo(name = "url_tz")
    var urlTZ: String? = null,
        @ColumnInfo(name = "plan_work")
    var planWork: String? = null,
        @ColumnInfo(name = "url_res")
    var urlRes: String? = null,
        @ColumnInfo(name = "list_square")
    var listSquare: String? = null,
        var price: Int? = null,
        @ColumnInfo(name = "url_report")
    var urlReport: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}