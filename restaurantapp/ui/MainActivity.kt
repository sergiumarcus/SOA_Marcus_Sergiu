package com.sergiumarcus.restaurantapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sergiumarcus.restaurantapp.R
import com.sergiumarcus.restaurantapp.logic.RestaurantsViewModel
import com.sergiumarcus.restaurantapp.model.Restaurant


class MainActivity : AppCompatActivity(), RestaurantsAdapter.RestaurantsListClickListener {

    lateinit var viewAdapter: RestaurantsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        viewAdapter = RestaurantsAdapter(this)

        val recyclerView = findViewById<RecyclerView>(R.id.cimenas_list).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context, viewManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val model = ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
        model.getRestaurants().observe(this, Observer<List<Restaurant>> { restaurants ->
            restaurantsReceived(restaurants)
        })
    }


    private fun restaurantsReceived(restaurants: List<Restaurant>?) {
        restaurants?.let {
            viewAdapter.setData(it)
        }
    }

    override fun onRestaurantClicked(restaurant: Restaurant) {
        val intent = Intent(this, TypesList::class.java)
        intent.putExtra("RESTAURANT_NAME", restaurant.name)
        intent.putExtra("RESTAURANT_ID", restaurant.id)
        startActivity(intent)
    }

}
