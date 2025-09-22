package com.aou.citysage.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aou.citysage.data.FirebaseRepository
import com.aou.citysage.data.models.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val firebaseRepository = FirebaseRepository()

    // State for appointment booking
    private val _appointmentState = MutableStateFlow<AppointmentState>(AppointmentState.Idle)
    val appointmentState: StateFlow<AppointmentState> = _appointmentState.asStateFlow()

    // State for places list
    private val _placesState = MutableStateFlow<PlacesState>(PlacesState.Loading)
    val placesState: StateFlow<PlacesState> = _placesState.asStateFlow()

    init {
        fetchPlaces()
    }

    fun fetchPlaces() {
        viewModelScope.launch {
            _placesState.value = PlacesState.Loading
            try {
                val places = firebaseRepository.getPlaces()
                _placesState.value = PlacesState.Success(places)
            } catch (e: Exception) {
                _placesState.value = PlacesState.Error("Failed to fetch places: ${e.message}")
                Log.e("HomeViewModel", "Error fetching places", e)
            }
        }
    }


}

sealed class AppointmentState {
    object Idle : AppointmentState()
    object Loading : AppointmentState()
    data class Success(val message: String) : AppointmentState()
    data class Error(val message: String) : AppointmentState()
}

sealed class PlacesState {
    object Loading : PlacesState()
    data class Success(val places: List<Place>) : PlacesState()
    data class Error(val message: String) : PlacesState()
}