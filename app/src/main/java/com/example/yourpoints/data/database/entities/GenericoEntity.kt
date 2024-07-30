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
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "dataCreated") val dataCreated: String,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "pointToInit") val pointToInit: Int,
    @ColumnInfo(name = "pointToFinish") val pointToFinish: Int?,
    @ColumnInfo(name = "finishToWin") val finishToWin: Boolean?,

    @ColumnInfo(name = "round") val round: Int?,
    @ColumnInfo(name = "roundPlayed") val roundPlayed: Int,

    @ColumnInfo(name = "player") val player: String
)


fun GenericoDomain.toEntity() = GenericoEntity(
    id = id,
    dataCreated = dataCreated,
    name = name,
    pointToInit = pointToInit,
    pointToFinish = pointToFinish,
    finishToWin = finishToWin,
    round = round,
    roundPlayed = roundPlayed,
    player = Gson().toJson(player),
)
/*
TableInfo{
    name='generico_table',
    columns={
        id=Column{
            name='id',
            type='INTEGER',
            affinity='3',
            notNull=true,
            primaryKeyPosition=1,
            defaultValue='undefined'
        },
        dataCreated=Column{
            name='dataCreated',
            type='TEXT',
            affinity='2',
            notNull=true,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        name=Column{
            name='name',
            type='TEXT',
            affinity='2',
            notNull=true,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        pointToInit=Column{
            name='pointToInit',
            type='INTEGER',
            affinity='3',
            notNull=true,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        pointToFinish=Column{
            name='pointToFinish',
            type='INTEGER',
            affinity='3',
            notNull=false,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        finishToWin=Column{
            name='finishToWin',
            type='BOOLEAN',
            affinity='1',
            notNull=false,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        round=Column{
            name='round',
            type='INTEGER',
            affinity='3',
            notNull=false,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        roundPlayed=Column{
            name='roundPlayed',
            type='INTEGER',
            affinity='3',
            notNull=true,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        player=Column{
            name='player',
            type='TEXT',
            affinity='2',
            notNull=true,
            primaryKeyPosition=0,
            defaultValue='undefined'
        }
    },
    foreignKeys=[],
    indices=[]
}
 */























