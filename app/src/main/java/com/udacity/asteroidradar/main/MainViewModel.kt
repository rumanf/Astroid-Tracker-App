package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.APODApi
import com.udacity.asteroidradar.api.NEOWService
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch


class MainViewModel (application: Application) : AndroidViewModel(application) {

    // database and repository variables
    private val database = getDatabase(application)
    private val repository = Repository(database)

    // The internal MutableLiveData String that stores the status of the most recent request

    private val _Asteroids = MutableLiveData<List<Asteroid>>()
    val Asteroids: LiveData<List<Asteroid>>
        get() = _Asteroids
    private val _navigateToSelectedAsteroid= MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get()=_navigateToSelectedAsteroid
    fun displayAsteroidDetails (asteroid: Asteroid){
        _navigateToSelectedAsteroid.value=asteroid
    }
    fun displayAsteroidDetailsComplete(){
        _navigateToSelectedAsteroid.value=null
    }

    private val _imageOfTheDay= MutableLiveData<PictureOfDay>()
    val imageOfTheDay: LiveData<PictureOfDay>
        get()=_imageOfTheDay

    val picofdaychangable=MutableLiveData<PictureOfDay>()
    // made these nullable in case there is no internet... then initialized it.
//     var picofdaytype:String?=""
//     var picofdayurl:String?=""
//    var picofdaytitle:String?=""

init {
    setuprepository()
    viewModelScope.launch {
        try {
            _imageOfTheDay?.value = APODApi?.APODretrofitService?.getPictureOfTheDay()
        } catch (e: Exception) {
            _imageOfTheDay?.value=PictureOfDay("image","This is the nasa image of the day","IMG")
            Log.i("message1","no internet for image")

        }
    }
}

    val asteroidlist = repository.asteroids

    // this function will use coroutine and setup the update the repository
    fun setuprepository(){
        viewModelScope.launch {
            try { repository.refreshAsteroids()}
            catch (e: Exception) {
                Log.i("message2","no internet Repo")

            }            }
    }

    // this gets image of the day using the api service for APOD, the connections are in service.kt
//     fun getImageOfTheDay(){
//    viewModelScope.launch {
//        try {
//            _imageOfTheDay.value = APODApi.APODretrofitService.getPictureOfTheDay()
//
//        } catch (e: Exception) { }
//    }
    // since this is commented out now, i need to use another way to check for media type...
//        picofdaytype=imageOfTheDay.value?.mediaType
//        if (picofdaytype=="image"){
//            picofdayurl=_imageOfTheDay.value?.url
//            picofdaytitle=_imageOfTheDay.value?.title
//        }
//        }



//////////////////////////////////////////////////////////////////



    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}



//add variable type of imageoftheday, then connect it to the parsing stuff to get data.