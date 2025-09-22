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

class PlaceDetailsViewModel : ViewModel()
{

    private val firebaseRepository = FirebaseRepository()

    private val _placeState = MutableStateFlow<PlaceState>(PlaceState.Loading)
    val placeState: StateFlow<PlaceState> = _placeState.asStateFlow()




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


}

sealed class PlaceState {
    object Loading : PlaceState()
    data class Success(val place: Place) : PlaceState()
    data class Error(val message: String) : PlaceState()
}