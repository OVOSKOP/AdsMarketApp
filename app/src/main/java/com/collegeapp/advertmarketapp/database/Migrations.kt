package com.collegeapp.advertmarketapp.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE orders ADD COLUMN status INTEGER NOT NULL DEFAULT 0")
        }
    }
}