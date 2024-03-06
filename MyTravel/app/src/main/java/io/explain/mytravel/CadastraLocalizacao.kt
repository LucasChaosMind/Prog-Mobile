package io.explain.mytravel

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import io.explain.mytravel.models.LocationData
import io.explain.mytravel.repository.LocationRepository

class CadastraLocalizacao : AppCompatActivity() {
    private lateinit var txLat: EditText
    private lateinit var txLong: EditText
    private lateinit var txDesc: EditText
    private lateinit var localBD: SQLiteDatabase
    private lateinit var locationRepository: LocationRepository;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_localizacao)
        txLat = findViewById(R.id.txLat)
        txLong = findViewById(R.id.txLong)
        txDesc = findViewById(R.id.txDesc)

        localBD = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath("dbfile.sqlite"), null)
        localBD.execSQL("CREATE TABLE IF NOT EXISTS mytravel ( _id INTEGER PRIMARY KEY AUTOINCREMENT, lat REAL, long REAL, descrytion TEXT)")

        locationRepository = LocationRepository(this)
    }

    fun saveNewLocalInDatabase(view: View) {
        val data = ContentValues();
        var lat = txLat.text.toString().toDouble()
        var long = txLong.text.toString().toDouble();
        var descrytion = txDesc.text.toString()

        if (descrytion.isNotEmpty()) {
            val lat = txLat.text.toString().toDoubleOrNull() ?: return
            val long = txLong.text.toString().toDoubleOrNull() ?: return
            val description = txDesc.text.toString()

            if (description.isNotEmpty()) {
                val locationData = LocationData(lat, long, description)
                locationRepository.insertLocationIntoDatabase(locationData)

                Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
            }
        }
    }
}