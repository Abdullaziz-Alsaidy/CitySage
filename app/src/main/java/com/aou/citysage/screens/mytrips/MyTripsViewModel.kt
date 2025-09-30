package com.aou.citysage.screens.mytrips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aou.citysage.data.FirebaseRepository
import com.aou.citysage.data.models.Booking
import com.aou.citysage.data.models.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyTripsViewModel : ViewModel()
{
    private val firebaseRepository = FirebaseRepository()

    // State Bookings
    private val _bookingsState = MutableStateFlow<BookingsState>(BookingsState.Loading)
    val bookingState: StateFlow<BookingsState> = _bookingsState.asStateFlow()

    init {
        fetchBookings()
    }

    fun fetchBookings() {
        Log.d("FAPI","fetchPlaces1")
        viewModelScope.launch {
            _bookingsState.value = BookingsState.Loading
            Log.d("FAPI","PlacesState.Loading")
            try {
                val bookings = firebaseRepository.getBookings()
                Log.d("FAPI","${bookings}")
                _bookingsState.value = BookingsState.Success(bookings)
            } catch (e: Exception) {
                _bookingsState.value = BookingsState.Error("Failed to fetch places: ${e.message}")
                Log.e("HomeViewModel", "Error fetching places", e)
            }
        }
    }



}



sealed class BookingsState {
    object Loading : BookingsState()
    data class Success(val bookings: List<Booking>) : BookingsState()
    data class Error(val message: String) : BookingsState()
}
