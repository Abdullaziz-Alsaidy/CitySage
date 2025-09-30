package com.aou.citysage.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aou.citysage.data.FirebaseRepository
import com.aou.citysage.data.models.Booking
import com.aou.citysage.data.models.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceDetailsViewModel : ViewModel()
{

    private val firebaseRepository = FirebaseRepository()

    private val _placeState = MutableStateFlow<PlaceState>(PlaceState.Loading)
    val placeState: StateFlow<PlaceState> = _placeState.asStateFlow()

    var showDialog by mutableStateOf(false)
        private set

    fun onDismissDialog() { showDialog = false }
    fun onBookClick() { showDialog = true }

    fun testuuid(){
        firebaseRepository.testid()
    }
    fun fetchPlace(placeID: String) {
        viewModelScope.launch {
            _placeState.value = PlaceState.Loading
            try {
                val place = firebaseRepository.getPlace(
                    placeID = placeID
                )
                place?.let { _placeState.value = PlaceState.Success(it) }
            } catch (e: Exception) {
                _placeState.value = PlaceState.Error("Failed to fetch places: ${e.message}")
                Log.e("HomeViewModel", "Error fetching places", e)
            }
        }
    }

    fun onConfirmBooking(booking: Booking) {
        viewModelScope.launch {
            try {

                firebaseRepository.createBooking(booking)
                showDialog = false // Close dialog after successful booking
                // Optionally update UI state or show a confirmation
            } catch (e: Exception) {
                Log.e("PlaceDetailsViewModel", "Error saving booking", e)
                // Handle error (e.g., show error message)
            }
        }
    }
}

sealed class PlaceState {
    object Loading : PlaceState()
    data class Success(val place: Place) : PlaceState()
    data class Error(val message: String) : PlaceState()
}