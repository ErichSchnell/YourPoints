package com.example.yourpoints.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.GenericoPlayerDomain
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.google.gson.Gson


@Entity(tableName = "generico_table")
data class GenericoEntity(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "dataCreated") val dataCreated: String = "",

    @ColumnInfo(name = "name") val name: String = "",

    @ColumnInfo(name = "withPoints") val withPoints:Boolean = false,
    @ColumnInfo(name = "pointToInit") val pointToInit:Int = 0,
    @ColumnInfo(name = "pointToFinish") val pointToFinish:Int = 100,
    @ColumnInfo(name = "finishToWin") val finishToWin:Boolean = true,

    @ColumnInfo(name = "withRounds") val withRounds:Boolean = false,
    @ColumnInfo(name = "round") val round:Int = 10,
    @ColumnInfo(name = "roundPlayed") val roundPlayed:Int = 1,

    @ColumnInfo(name = "player") val player:String
)


fun GenericoDomain.toEntity() = GenericoEntity(
    id = id,
    dataCreated = dataCreated,
    name = name,
    withPoints = withPoints,
    pointToInit = pointToInit,
    pointToFinish = pointToFinish,
    finishToWin = finishToWin,
    withRounds = withRounds,
    round = round,
    roundPlayed = roundPlayed,
    player = Gson().toJson(player),
)

