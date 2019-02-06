package com.sergiumarcus.restaurantapp.network

import com.sergiumarcus.restaurantapp.model.Restaurant
import com.sergiumarcus.restaurantapp.model.Type
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestaurantServiceApi {
    @get:GET("/restaurants")
    val allRestaurants: Call<List<Restaurant>>

    @GET("/types/{id}")
    fun typesForRestaurants(@Path(value = "id") id: Int): Call<List<Type>>
}

object RestaurantClientInstance {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://10.0.2.2:8081"

    val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}