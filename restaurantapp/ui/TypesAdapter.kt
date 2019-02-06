package com.sergiumarcus.restaurantapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sergiumarcus.restaurantapp.R
import com.sergiumarcus.restaurantapp.model.Type
import java.util.*

public class TypesAdapter() : RecyclerView.Adapter<TypesAdapter.ViewHolder>() {

    private val typesList = ArrayList<Type>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeName: TextView = view.findViewById(R.id.type_name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.type_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return typesList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.typeName.text = typesList[position].title

    }

    fun setData(restaurants: List<Type>) {
        typesList.clear()
        typesList.addAll(restaurants)
        notifyDataSetChanged()
    }

}