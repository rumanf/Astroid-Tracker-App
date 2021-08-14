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
         * A playlist of videos that can be shown on the screen.
         */
        val asteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getAsteroids()) {
                it.asDomainModel()
            }

        /**
         * Refresh the videos stored in the offline cache.
         *
         * This function uses the IO dispatcher to ensure the database insert database operation
         * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
         * function is now safe to call from any thread including the Main thread.
         *
         * To actually load the videos for use, observe [videos]
         */
        suspend fun refreshAsteroids() {
            withContext(Dispatchers.IO) {

                val asteroidlistunparsed = Network.asteroids.getAsteroidList(getdates()[0], getdates()[1], Constants.API_KEY).await()
                //val asteroidlistunparsed = Network.asteroids.getAsteroidList("2021-04-10", "2021-04-17", Constants.API_KEY).await()
               val jsonResponse = JSONObject(asteroidlistunparsed)
                val asteroidlist = parseAsteroidsJsonResult(jsonResponse)

                database.asteroidDao.insertAll(*asteroidlistunparsed.asDatabaseModel())

            }
        }
    private fun getdates(): Array<String> {

        val calendar = Calendar.getInstance()
        val dateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,Locale.getDefault())
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val startdate = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
        val enddate = dateFormat.format(calendar.time)

        return listOf(startdate, enddate).toTypedArray()
    }
    }
