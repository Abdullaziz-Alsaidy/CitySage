package com.aou.citysage.screens.mytrips

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aou.citysage.data.FirebaseRepository
import com.aou.citysage.data.models.Booking
import com.aou.citysage.data.models.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MyTripsViewModel : ViewModel()
{
    private val firebaseRepository = FirebaseRepository()

    // State Bookings
    private val _bookingsState = MutableStateFlow<BookingsState>(BookingsState.Loading)
    val bookingState: StateFlow<BookingsState> = _bookingsState.asStateFlow()
    private val _selectedFilter = MutableStateFlow("Today")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    init {
        fetchBookings()
    }

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
        fetchBookings()
    }

    fun fetchBookings() {
        Log.d("FAPI", "fetchBookings")
        viewModelScope.launch {
            _bookingsState.value = BookingsState.Loading
            Log.d("FAPI", "BookingsState.Loading")
            try {
                val bookings = firebaseRepository.getBookings()
                Log.d("FAPI", "Bookings: $bookings")

                // Filter bookings based on selected filter
                val filteredBookings = filterBookings(bookings, _selectedFilter.value)
                _bookingsState.value = BookingsState.Success(filteredBookings)
            } catch (e: Exception) {
                _bookingsState.value = BookingsState.Error("Failed to fetch bookings: ${e.message}")
                Log.e("HomeViewModel", "Error fetching bookings", e)
            }
        }
    }


    private fun filterBookings(bookings: List<Booking>, filter: String): List<Booking> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d")

        return when (filter) {
            "Today" -> bookings.filter { booking ->
                try {
                    val bookingDate = LocalDate.parse(booking.bookingDate, formatter)
                    bookingDate == today
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Invalid date format for booking: ${booking.bookingDate}")
                    false
                }
            }
            "Past Trips" -> bookings.filter { booking ->
                try {
                    val bookingDate = LocalDate.parse(booking.bookingDate, formatter)
                    bookingDate.isBefore(today)
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Invalid date format for booking: ${booking.bookingDate}")
                    false
                }
            }
            "Upcoming" -> bookings.filter { booking ->
                try {
                    val bookingDate = LocalDate.parse(booking.bookingDate, formatter)
                    bookingDate.isAfter(today)
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Invalid date format for booking: ${booking.bookingDate}")
                    false
                }
            }
            else -> bookings // Return all bookings if filter is invalid
        }
    }
}





sealed class BookingsState {
    object Loading : BookingsState()
    data class Success(val bookings: List<Booking>) : BookingsState()
    data class Error(val message: String) : BookingsState()
}
