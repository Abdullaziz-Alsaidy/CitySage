package com.uni.sehhaty.Utilitie

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
@Serializable

data class Hospital(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val city: String = "",
    val specialties: List<String> = emptyList()
)
@Serializable

data class Doctor(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val specialty: String = "",
    val hospitalId: String = "",  // Reference to Hospital document
    val clinicName: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val availableDays: List<String> = emptyList()
)
@Serializable
data class Appointment(
    val id: String = "",
    val userId: String = "",  // Firebase Auth UID
    val doctorId: String = "",  // Reference to Doctor document
    val hospitalId: String = "",  // Reference to Hospital document
    val date: String = "",
    val time: String = "",
    val status: String = "Pending",
    val clinicName : String ="",
    val doctorName: String = "",

)


// Optional: User profile data (separate from auth)
@Serializable
data class UserProfile(
    val uid: String = "",  // Matches Firebase Auth UID
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = ""
)

data class PaymentMethod(val name: String, @DrawableRes val iconRes: Int)
