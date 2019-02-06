package com.sergiumarcus.restaurantapp.logic

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.sergiumarcus.restaurantapp.model.Restaurant
import com.sergiumarcus.restaurantapp.network.RestaurantClientInstance
import com.sergiumarcus.restaurantapp.network.RestaurantServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class RestaurantsViewModel(val app: Application) : AndroidViewModel(app) {

    private lateinit var restaurants: MutableLiveData<List<Restaurant>>
    private lateinit var restaurantsService: RestaurantServiceApi

    fun getRestaurants(): LiveData<List<Restaurant>> {
        if (!::restaurants.isInitialized) {
            restaurantsService = RestaurantClientInstance.retrofitInstance.create(RestaurantServiceApi::class.java)
            restaurants = MutableLiveData()
            loadRestaurants()
        }
        return restaurants
    }

    private fun loadRestaurants() {
        val restaurantsList = restaurantsService.allRestaurants
        restaurantsList.enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                restaurants.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        })
    }
}