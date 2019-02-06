package com.sergiumarcus.restaurantapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sergiumarcus.restaurantapp.R
import com.sergiumarcus.restaurantapp.model.Restaurant

public class RestaurantsAdapter(val listener: RestaurantsListClickListener):
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    public interface RestaurantsListClickListener {
        fun onRestaurantClicked(restaurant: Restaurant)
    }

    private val restaurantsList = ArrayList<Restaurant>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantName: TextView = view.findViewById(R.id.restaurant_name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.restaurantName.text = restaurantsList[position].name
        viewHolder.itemView.setOnClickListener { listener.onRestaurantClicked(restaurantsList[position]) }

    }

    fun setData(restaurants: List<Restaurant>) {
        restaurantsList.clear()
        restaurantsList.addAll(restaurants)
        notifyDataSetChanged()
    }

}