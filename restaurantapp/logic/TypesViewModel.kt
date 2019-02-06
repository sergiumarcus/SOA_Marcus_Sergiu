package com.sergiumarcus.restaurantapp.logic

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.sergiumarcus.restaurantapp.model.Type
import com.sergiumarcus.restaurantapp.network.RestaurantClientInstance
import com.sergiumarcus.restaurantapp.network.RestaurantServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class TypesViewModel(val app: Application) : AndroidViewModel(app) {

    private lateinit var types: MutableLiveData<List<Type>>
    private lateinit var restaurantService: RestaurantServiceApi
    private var restaurantId: Int? = null

    public fun setRestaurantId(id: Int) {
        restaurantId = id
    }

    fun getUsers(): LiveData<List<Type>> {
        if (!::types.isInitialized) {
            restaurantService = RestaurantClientInstance.retrofitInstance.create(RestaurantServiceApi::class.java)
            types = MutableLiveData()
            loadRestaurants()
        }
        return types
    }

    private fun loadRestaurants() {
        restaurantId?.let {
            val restaurantsList = restaurantService.typesForRestaurants(it)
            restaurantsList.enqueue(object : Callback<List<Type>> {
                override fun onResponse(call: Call<List<Type>>, response: Response<List<Type>>) {
                    types.postValue(response.body())
                }

                override fun onFailure(call: Call<List<Type>>, t: Throwable) {
                    Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show();
                }
            })
        }
    }
}