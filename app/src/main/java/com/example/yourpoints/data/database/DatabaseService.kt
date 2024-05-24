package com.example.yourpoints.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourpoints.data.database.dao.GenericoDao
import com.example.yourpoints.data.database.dao.TrucoDao
import com.example.yourpoints.data.database.entities.GenericoEntity
import com.example.yourpoints.data.database.entities.TrucoEntity

@Database(entities = [TrucoEntity::class, GenericoEntity::class], version = 1)
abstract class DatabaseService: RoomDatabase() {
    abstract fun getTrucoDao(): TrucoDao
    abstract fun getGenericoDao(): GenericoDao
}