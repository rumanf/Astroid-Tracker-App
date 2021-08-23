package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).selectedAsteroid

        binding.asteroid = asteroid

        //setting content description
        binding.absoluteMagnitude.contentDescription="The absolute magnitude is ${asteroid.absoluteMagnitude} au"
        binding.relativeVelocity.contentDescription="The Relative Velocity is ${asteroid.relativeVelocity} kilometers per second"
        binding.estimatedDiameter.contentDescription="The Estimated Diameter is ${asteroid.estimatedDiameter} kilometers"
        binding.distanceFromEarth.contentDescription="The Distance from Earth is ${asteroid.distanceFromEarth} Au"


        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
