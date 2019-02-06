package com.sergiumarcus.restaurantapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sergiumarcus.restaurantapp.R
import com.sergiumarcus.restaurantapp.logic.TypesViewModel
import com.sergiumarcus.restaurantapp.model.Type

class TypesList : AppCompatActivity() {

    private lateinit var viewAdapter: TypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_types_list)
        val viewManager = LinearLayoutManager(this)
        viewAdapter = TypesAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.types_list).apply {
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

        val model = ViewModelProviders.of(this).get(TypesViewModel::class.java)
        model.setRestaurantId(intent.getIntExtra("RESTAURANT_ID", -1))
        model.getUsers().observe(this, Observer<List<Type>> { types ->
            typesReceived(types)
        })
    }

    private fun typesReceived(types: List<Type>?) {
        types?.let {
            viewAdapter.setData(it)
        }
    }
}

