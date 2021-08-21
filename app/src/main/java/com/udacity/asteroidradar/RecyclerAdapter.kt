package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroiditemBinding

//view holder class created here, it is connected to the astroiditel xml
class ItemViewHolder(val viewDataBinding: AsteroiditemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.asteroiditem
    }
}


class RecyclerViewAdapter(val clicklistener: AsteroidClick): RecyclerView.Adapter<ItemViewHolder>(){

    //this is the data variable for asteroidlist
    var data: List<Asteroid> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }
    //gets the size of the list as required by the recyclerview
    override fun getItemCount() =data.size

   // called when recyclerview creates a new viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val withDataBinding: AsteroiditemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ItemViewHolder.LAYOUT,
            parent,
            false)
        return ItemViewHolder(withDataBinding)
    }


    //sets the data to the viewholder items as required
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.asteroid = data[position]
            it.asteroidclicked=clicklistener

            // could we set a click listener on the layout, then when it is clicked it changes a variables value, then livadata observer can use navigation to navigate to next page. how we we pass the id though?
        }
    }


    }

class AsteroidClick(val block: (Asteroid) -> Unit) {

    fun onClick(asteroid: Asteroid) = block(asteroid)
}






