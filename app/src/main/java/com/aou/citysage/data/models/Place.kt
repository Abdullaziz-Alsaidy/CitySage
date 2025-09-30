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
    //@PropertyName("images") val images: List<String> = emptyList(), // Multiple images
    @PropertyName("category") val category: String = "category", // Sports, Adventure, etc.
    @PropertyName("duration") val duration: String = "duration", // "2 hours", "Full day"
    @PropertyName("capacity") val capacity: Int = 0, // Max people per session
    @PropertyName("address") val address: String = "address",
    @PropertyName("phone") val phone: String = "phone",
    @PropertyName("website") val website: String = "website",
    @PropertyName("latitude") val latitude: String = "website",
    @PropertyName("longitude") val longitude: String = "website"
)

data class Booking(
    val id: String = "",
    // Link to the booked Place
    @PropertyName("placeId") val placeId: String = "",
    // Link to the user making the booking
    @PropertyName("userId") val userId: String = "",

    // Booking Details
    @PropertyName("bookingDate") val bookingDate: String = "", // e.g., "YYYY-MM-DD"
    @PropertyName("startTime") val startTime: String = "",     // e.g., "HH:MM"
    @PropertyName("endTime") val endTime: String = "",         // Calculated based on startTime and duration, e.g., "HH:MM"
    @PropertyName("numberOfPeople") val numberOfPeople: Int = 1,
    @PropertyName("duration") val duration: String = "",        // Copied from Place, or selected if variable

    // Financial & Status
    @PropertyName("totalPrice") val totalPrice: Int = 0, // Price * numberOfPeople (plus taxes/fees)
    @PropertyName("status") val status: String = "PENDING", // e.g., "PENDING", "CONFIRMED", "CANCELLED"

    // Metadata
    @PropertyName("createdAt") val createdAt: String = "" // Timestamp of when the booking was created
)