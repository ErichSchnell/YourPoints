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

@Database(entities = [TrucoEntity::class, GenericoEntity::class], version = 3)
abstract class DatabaseService: RoomDatabase() {
    abstract fun getTrucoDao(): TrucoDao
    abstract fun getGenericoDao(): GenericoDao
}

//val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE GenericoEntity DELETE COLUMN playerMax INTEGER DEFAULT 0 NOT NULL")
//    }
//}
//
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {

        // Eliminar la tabla antigua
        database.execSQL("DROP TABLE generico_table")

        // Crear la nueva tabla
        database.execSQL("""
            CREATE TABLE generico_table (
                id INTEGER PRIMARY KEY NOT NULL,
                dataCreated TEXT NOT NULL,
                name TEXT NOT NULL,
                pointToInit INTEGER NOT NULL,
                pointToFinish INTEGER,
                finishToWin BOOLEAN,
                round INTEGER,
                roundPlayed INTEGER NOT NULL,
                player TEXT NOT NULL
            )
        """)

       /* // Crear nueva tabla sin la columna "playerMax"
        database.execSQL("""
            CREATE TABLE generico_table_new (
                id INTEGER PRIMARY KEY NOT NULL,
                dataCreated TEXT NOT NULL,
                name TEXT NOT NULL,
                pointToInit INTEGER NOT NULL,
                pointToFinish INTEGER,
                finishToWin BOOLEAN,
                round INTEGER,
                roundPlayed INTEGER NOT NULL,
                player TEXT NOT NULL
            )
        """)

        // Copiar datos de la tabla antigua a la nueva tabla
        database.execSQL("""
            INSERT INTO generico_table_new (id, dataCreated, name, pointToInit, pointToFinish, finishToWin, round, roundPlayed, player)
            SELECT id, dataCreated, name, pointToInit, pointToFinish, finishToWin, round, roundPlayed, player FROM generico_table
        """)

        // Eliminar la tabla antigua
        database.execSQL("DROP TABLE generico_table")

        // Renombrar la nueva tabla a la tabla original
        database.execSQL("ALTER TABLE generico_table_new RENAME TO generico_table")*/
    }
}




/*
TableInfo{
    name='generico_table',
    columns={
        pointToFinish = Column {
            name='pointToFinish',
            type='INTEGER',
            affinity='3',
            notNull=false,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        round = Column {
            name='round',
            type='INTEGER',
            affinity='3',
            notNull=false,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        finishToWin=Column{
            name='finishToWin',
            type='INTEGER',
            affinity='3',
            notNull=false,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        name=Column{
            name='name',
            type='TEXT', affinity='2',
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
        roundPlayed=Column{
            name='roundPlayed',
            type='INTEGER',
            affinity='3',
            notNull=true,
            primaryKeyPosition=0,
            defaultValue='undefined'
        },
        player=Column{name='player',
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





**/