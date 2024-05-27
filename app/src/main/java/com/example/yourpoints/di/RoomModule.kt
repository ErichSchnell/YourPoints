package com.example.yourpoints.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.yourpoints.data.database.DatabaseService
import com.example.yourpoints.data.database.dao.TrucoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val ANNOTATOR_DATABASE_NAME = "annotator_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): DatabaseService{
        return Room.databaseBuilder(context, DatabaseService::class.java, ANNOTATOR_DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideTrucoDao(db:DatabaseService) = db.getTrucoDao()

    @Singleton
    @Provides
    fun provideGenericoDao(db:DatabaseService) = db.getGenericoDao()
}
