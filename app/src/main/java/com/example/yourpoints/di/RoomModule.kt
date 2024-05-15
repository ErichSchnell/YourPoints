package com.example.yourpoints.di

import android.content.Context
import androidx.room.Room
import com.example.yourpoints.data.database.DatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val TRUCO_DATABASE_NAME = "truco_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DatabaseService::class.java, TRUCO_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideTrucoDao(db:DatabaseService) = db.getTrucoDao()
}