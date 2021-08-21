package com.udacity.asteroidradar.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidClick
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.RecyclerViewAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this,
            MainViewModel.Factory(activity.application)
        ).get(MainViewModel::class.java)
    }


     private var viewModelAdapter: RecyclerViewAdapter?=null

   // this will connect the info from the viewmodel to the adapter.



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModelvar = viewModel

        //connect viewmodel adapter with the clicklistener
        viewModelAdapter= RecyclerViewAdapter(AsteroidClick {
            viewModel.displayAsteroidDetails(it)
            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            //val packageManager = context?.packageManager ?: return@AsteroidClick

        })
        binding.asteroidRecycler.adapter = viewModelAdapter
        //update recycler data
        viewModel.asteroidlist.observe(viewLifecycleOwner, Observer{ astroidlist ->
            astroidlist?.apply {
                viewModelAdapter?.data= astroidlist
            }
        })


        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayAsteroidDetailsComplete()
            }
        })
//        binding.root.findViewById<RecyclerView>(R.id.asteroid_recycler).apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter=viewModelAdapter
//        }
// one way of displaying image of the day using glide, but i will try binding adapters

        setHasOptionsMenu(false)

        return binding.root
    }




    ///function i can use to disply image of the day, but will try binding adapter first
//    private fun imageoftheday(imageurl:String?, var imagedisplaylocation) {
//
//        Glide.with(this).load(imageurl)
//            .fitCenter()
//            .diskCacheStrategy()
//            .placeholder(R.drawable.placeholder_picture_of_day) // put placeholder
//            .into(imagedisplaylocation)
//    }


}

