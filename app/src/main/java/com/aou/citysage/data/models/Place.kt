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
    @PropertyName("city") val city: String = ""
)