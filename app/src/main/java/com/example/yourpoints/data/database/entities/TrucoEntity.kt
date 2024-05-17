package com.example.yourpoints.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yourpoints.domain.model.TrucoDomain

@Entity(tableName = "truco_table")
data class TrucoEntity (
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "pointLimit") val pointLimit:Int = 0,
)


fun TrucoDomain.toEntity() = TrucoEntity(
    id = id,
    pointLimit = pointLimit
)