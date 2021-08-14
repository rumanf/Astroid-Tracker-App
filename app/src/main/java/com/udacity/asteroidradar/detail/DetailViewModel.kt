package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid

class DetailViewModel (Asteriod: Asteroid, app: Application) : AndroidViewModel(app){

        private val _selectedAsteroid = MutableLiveData<Asteroid>()

        // The external LiveData for the SelectedAsteroid
        val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

        // Initialize the _selectedProperty MutableLiveData to get value from the argument passed when the viewmodel is created
        init {
            _selectedAsteroid.value = Asteriod
        }



}