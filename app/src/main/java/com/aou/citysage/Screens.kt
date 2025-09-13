package com.aou.citysage

import com.uni.sehhaty.Utilitie.Doctor
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object LoginPage

    @Serializable
    data object CitySagePage

    @Serializable
    data class HospitalDetailsPage(val hospitalId: String)

    @Serializable
    data class DoctorDetailsPage(val doctorId: String)

    @Serializable
    data object AppointmentsPage

    @Serializable
    data object ResultsPage

    @Serializable
    data object ProfilePage
    @Serializable
    data object LabPage
    @Serializable
    data object MedicinePage

}