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

class HomeViewModel : ViewModel()
{
    private val _myValue = MutableStateFlow(false)
    val myValue: StateFlow<Boolean> = _myValue.asStateFlow()
    private val firebaseRepository = FirebaseRepository()

    // State for places list
    private val _placesState = MutableStateFlow<PlacesState>(PlacesState.Loading)
    val placesState: StateFlow<PlacesState> = _placesState.asStateFlow()

    //private val _placeState = MutableStateFlow<PlaceState>(PlaceState.Loading)
    //val placeState: StateFlow<PlaceState> = _placeState.asStateFlow()

    init {
        fetchPlaces()
    }
    fun toggleMyValue() {
        // ViewModel logic to update the state
        _myValue.value = !_myValue.value
        // You can also add logging here if needed for ViewModel-level debugging
        // Log.d("MyViewModel", "Toggled to ${_myValue.value}")
    }
    fun fetchPlaces() {
        Log.d("FAPI","fetchPlaces1")
        viewModelScope.launch {
            _placesState.value = PlacesState.Loading
            Log.d("FAPI","PlacesState.Loading")
            try {
                val places = firebaseRepository.getPlaces()
                Log.d("FAPI","${places}")
                _placesState.value = PlacesState.Success(places)
            } catch (e: Exception) {
                _placesState.value = PlacesState.Error("Failed to fetch places: ${e.message}")
                Log.e("HomeViewModel", "Error fetching places", e)
            }
        }
    }

     fun addFavorite(placeId: String) {
        viewModelScope.launch {
            firebaseRepository.addFavorite(placeId)
        }
    }



}



sealed class PlacesState {
    object Loading : PlacesState()
    data class Success(val places: List<Place>) : PlacesState()
    data class Error(val message: String) : PlacesState()
}


