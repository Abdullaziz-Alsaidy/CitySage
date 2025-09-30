package com.aou.citysage.screens.favorite

import androidx.lifecycle.ViewModel
import com.aou.citysage.data.FirebaseRepository
import com.aou.citysage.screens.mytrips.BookingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository()

    // State Bookings
    private val _bookingsState = MutableStateFlow<BookingsState>(BookingsState.Loading)
    val bookingState: StateFlow<BookingsState> = _bookingsState.asStateFlow()
    private val _selectedFilter = MutableStateFlow("Today")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

}