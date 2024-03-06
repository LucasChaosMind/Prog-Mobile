package io.explain.mytravel.repository

import android.content.ContentValues
import io.explain.mytravel.models.LocationData
import android.content.Context

class LocationRepository (private val context: Context) {
    fun fetchLocationDataFromDatabase(): List<LocationData> {
        val dbHelper = LocationDataHelper(context)
        val locationDataList = mutableListOf<LocationData>()
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            LocationContract.COLUMN_ID,
            LocationContract.COLUMN_LATITUDE,
            LocationContract.COLUMN_LONGITUDE,
            LocationContract.COLUMN_DESCRIPTION
        )

        val cursor = db.query(
            LocationContract.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val location = LocationData(
                    getDouble(getColumnIndexOrThrow(LocationContract.COLUMN_LATITUDE)),
                    getDouble(getColumnIndexOrThrow(LocationContract.COLUMN_LONGITUDE)),
                    getString(getColumnIndexOrThrow(LocationContract.COLUMN_DESCRIPTION))
                )
                locationDataList.add(location)
            }
        }
        cursor.close()
        db.close()

        return locationDataList
    }
    
    fun insertLocationIntoDatabase(location: LocationData) {
        val dbHelper = LocationDataHelper(context)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(LocationContract.COLUMN_LATITUDE, location.latitude)
            put(LocationContract.COLUMN_LONGITUDE, location.longitude)
            put(LocationContract.COLUMN_DESCRIPTION, location.description)
        }

        db.insert(LocationContract.TABLE_NAME, null, values)
        db.close()
    }
    fun deleteLocation(description: String) {
        val dbHelper = LocationDataHelper(context)
        val db = dbHelper.writableDatabase
        db.delete("mytravel", "description = ?", arrayOf(description))
        db.close()
    }
}