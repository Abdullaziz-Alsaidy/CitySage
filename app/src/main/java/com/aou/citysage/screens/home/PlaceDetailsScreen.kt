package com.aou.citysage.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aou.citysage.AppText
import com.aou.citysage.R
import com.aou.citysage.data.models.Place

@Composable
fun PlaceDetailsScreen(
    placeID: String,
    viewModel: PlaceDetailsViewModel = viewModel()
) {
    Log.d("PlaceDetailsScreen", "Received placeID: $placeID")
    val placeState by viewModel.placeState.collectAsState()

    LaunchedEffect(placeID) {
        viewModel.fetchPlace(placeID)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (placeState) {
            is PlaceState.Success -> {
                val place = (placeState as PlaceState.Success).place
                Log.d("PlaceDetailsScreen", "Success state with place: ${place.name}")
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = place.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = place.details,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "City: ${place.city}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Rating: ${place.rating}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Open: ${place.openTimes}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Distance: ${place.distance}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Price: ${place.price}",
                        fontSize = 16.sp
                    )
                }
            }
            is PlaceState.Loading -> {
                CircularProgressIndicator()
            }
            is PlaceState.Error -> {
                val errorMessage = (placeState as PlaceState.Error).message
                Log.e("PlaceDetailsScreen", "Error: $errorMessage")
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


