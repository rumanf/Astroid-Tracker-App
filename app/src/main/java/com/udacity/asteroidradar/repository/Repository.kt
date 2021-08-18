package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class Repository (private val database: AsteroidDatabase) {

        /**
         * A list of asteroids that can be shown on the screen.
         */
        val asteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getAsteroids()) {
                it.asDomainModel()}

        suspend fun refreshAsteroids() {
            withContext(Dispatchers.IO) {

                val asteroidlistunparsed = Network.asteroids.getAsteroidList(getdates()[0], getdates()[1], Constants.API_KEY)
               val jsonResponse = JSONObject(asteroidlistunparsed)
                val asteroidlistparsed = parseAsteroidsJsonResult(jsonResponse)

                database.asteroidDao.insertAll(*asteroidlistparsed.asDatabaseModel())

            }
        }
    private fun getdates(): Array<String> {

        val calendar = Calendar.getInstance()
        val dateFormat =SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,Locale.getDefault())

        val startdate = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
        val enddate = dateFormat.format(calendar.time)

        return listOf(startdate, enddate).toTypedArray()
    }
    }
