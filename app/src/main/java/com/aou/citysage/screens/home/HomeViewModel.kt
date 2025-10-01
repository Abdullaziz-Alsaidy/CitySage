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

    fun fetchPlaces() {
        viewModelScope.launch {
            _placesState.value = PlacesState.Loading
            try {
                val places = firebaseRepository.getPlaces()
                val favorites = firebaseRepository.getFavorites() // suspend function

                val updatedPlaces = places.map { place ->
                    place.copy(isFavorite = favorites.contains(place.id))
                }

                _placesState.value = PlacesState.Success(updatedPlaces)

            } catch (e: Exception) {
                _placesState.value = PlacesState.Error("Failed to fetch places: ${e.message}")
                Log.e("HomeViewModel", "Error fetching places", e)
            }
        }
    }



    fun toggleFavorite(placeId: String) {
        viewModelScope.launch {
            val currentState = _placesState.value
            if (currentState is PlacesState.Success) {
                val updatedPlaces = currentState.places.map { place ->
                    if (place.id == placeId) {
                        val newIsFavorite = !place.isFavorite
                        // Optimistic update: toggle locally for instant UI refresh
                        place.copy(isFavorite = newIsFavorite)
                    } else {
                        place
                    }
                }
                // Update state immediately
                _placesState.value = PlacesState.Success(updatedPlaces)

                try {
                    // Persist to repository asynchronously
                    if (updatedPlaces.find { it.id == placeId }?.isFavorite == true) {
                        firebaseRepository.addFavorite(placeId)
                    } else {
                        firebaseRepository.removeFavorite(placeId) // Add this method to FirebaseRepository if missing
                    }
                } catch (e: Exception) {
                    // On error, revert the optimistic update and refetch
                    Log.e("HomeViewModel", "Error toggling favorite", e)
                    fetchPlaces()
                }
            }
        }
    }



}



sealed class PlacesState {
    object Loading : PlacesState()
    data class Success(val places: List<Place>) : PlacesState()
    data class Error(val message: String) : PlacesState()
}


