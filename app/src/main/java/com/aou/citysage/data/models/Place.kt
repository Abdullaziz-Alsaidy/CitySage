package com.aou.citysage.data.models

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable


@Serializable
data class Place(
    val  id: String ="",
    @PropertyName("name") val name: String = "",
    @PropertyName("details") val details: String = "",
    @PropertyName("openTimes") val openTimes: String = "",
    @PropertyName("rating") val rating: Double = 0.0,
    @PropertyName("distance") val distance: String = "",
    @PropertyName("price") val price: Int = 0,
    @PropertyName("city") val city: String = "",
    // Added New
    // Additional fields you'll need:
    @PropertyName("images") val images: List<String> = emptyList(), // Multiple images
    @PropertyName("category") val category: String = "category", // Sports, Adventure, etc.
    @PropertyName("duration") val duration: String = "duration", // "2 hours", "Full day"
    @PropertyName("capacity") val capacity: Int = 0, // Max people per session
    @PropertyName("address") val address: String = "address",
    @PropertyName("phone") val phone: String = "phone",
    @PropertyName("website") val website: String = "website",
    @PropertyName("latitude") val latitude: Double = 0.0,
    @PropertyName("longitude") val longitude: Double = 0.0
)