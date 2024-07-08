package com.example.yourpoints.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.yourpoints.data.database.dao.GenericoDao
import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.GenericoEntity
import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.data.database.model.GenericoPlayerConverter

@Database(entities = [TrucoEntity::class, GenericoEntity::class], version = 2)
abstract class DatabaseService: RoomDatabase() {
    abstract fun getTrucoDao(): TrucoDao
    abstract fun getGenericoDao(): GenericoDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE GenericoEntity DELETE COLUMN playerMax INTEGER DEFAULT 0 NOT NULL")
    }
}