package com.example.takeofflabstinder.data.datasource.local

import org.json.JSONObject

data class ProfileEntity(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val city: String,
    val country: String,
    var isMatch: Boolean,
    val photos: List<String>
)

fun JSONObject.toProfileEntity() = ProfileEntity(
    id = getInt("id"),
    firstName = getString("first_name"),
    lastName = getString("last_name"),
    city = getString("city"),
    country = getString("country"),
    isMatch = getBoolean("is_match"),
    photos = run {
        val jsonPhotos = getJSONArray("photos")
        val photos = mutableListOf<String>()
        for (i in 0..jsonPhotos.length()) {
            photos.add(jsonPhotos.getString(i))
        }
        photos
    }
)