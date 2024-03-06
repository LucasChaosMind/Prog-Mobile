package io.explain.mytravel.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.explain.mytravel.models.LocationData
import io.explain.mytravel.repository.LocationContract

class LocationDataHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE ${LocationContract.TABLE_NAME} (
                ${LocationContract.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${LocationContract.COLUMN_LATITUDE} REAL,
                ${LocationContract.COLUMN_LONGITUDE} REAL,
                ${LocationContract.COLUMN_DESCRIPTION} TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implemente a lógica de atualização do banco de dados, se necessário
    }

    companion object {
        const val DATABASE_NAME = "locations.db"
        const val DATABASE_VERSION = 1
    }
}

