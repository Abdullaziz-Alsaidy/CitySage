package com.aou.citysage

import com.uni.sehhaty.Utilitie.Doctor
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object LoginPage

    @Serializable
    data object HomePage

    @Serializable
    data object FavoritePage

    @Serializable
    data object MyTripsPage

    @Serializable
    data object MyProfilePage


}