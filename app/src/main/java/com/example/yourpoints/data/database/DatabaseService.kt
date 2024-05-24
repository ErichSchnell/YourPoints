package com.example.yourpoints.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.yourpoints.data.database.dao.GenericoDao
import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.GenericoEntity
import com.example.yourpoints.data.database.entities.TrucoEntity
import com.example.yourpoints.data.database.model.GenericoPlayerConverter

@Database(entities = [TrucoEntity::class, GenericoEntity::class], version = 1)
//@TypeConverters(GenericoPlayerConverter::class)
abstract class DatabaseService: RoomDatabase() {
    abstract fun getTrucoDao(): TrucoDao
    abstract fun getGenericoDao(): GenericoDao
}