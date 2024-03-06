package io.explain.mytravel.adapter

import io.explain.mytravel.models.LocationData

interface OnItemClickListener {
    fun onItemClick(locationData: LocationData)
    fun onDeleteClick(description: String)
}