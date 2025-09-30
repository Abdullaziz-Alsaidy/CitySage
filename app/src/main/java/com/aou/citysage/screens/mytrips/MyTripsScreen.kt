package com.aou.citysage.screens.mytrips

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aou.citysage.AppText
import com.aou.citysage.FilterMenu
import com.aou.citysage.FilterMenu1
import com.aou.citysage.R
import com.aou.citysage.data.models.Booking
import com.aou.citysage.data.models.Place

import com.aou.citysage.screens.favorite.FavoriteCard



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyTripsScreen(
    viewModel: MyTripsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scrollState = rememberScrollState()
    val bookingState by viewModel.bookingState.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .fillMaxHeight()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* Navigate to new trip */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text(text = "New Trip  ")
                    Text(text = "+", fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("رحلاتي", fontSize = 45.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Category Tabs
            val categories = listOf("Today", "Past Trips", "Upcoming")
            Row {
                categories.forEachIndexed { index, category ->
                    FilterMenu1(
                        text = category,
                        isSelected = selectedFilter == category,
                        onClick = { viewModel.setFilter(category) }
                    )
                }
            }

            // Booking List
            when (bookingState) {
                is BookingsState.Loading -> {
                    Text("Loading...", fontSize = 18.sp)
                }

                is BookingsState.Error -> {
                    Text(
                        text = (bookingState as BookingsState.Error).message,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }

                is BookingsState.Success -> {
                    val bookings = (bookingState as BookingsState.Success).bookings
                    if (bookings.isEmpty()) {
                        Text("No trips found", fontSize = 18.sp)
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            bookings.forEach { booking ->
                                MyTripsCard(booking)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MyTripsCard(item: Booking) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.quran), // Replace with booking.place image if available
            contentDescription = "Place Image",
        )
        Column(
            modifier = Modifier
                .height(140.dp)
                .background(Color(0xFFF5F5F5))
                .fillMaxWidth()
        ) {
            // Title (Booking ID or Place ID)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AppText(
                    text = "Booking #${item.id}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 4.dp),
                    fontSize = 20.sp,
                    isBold = true
                )
            }

            // Location (placeholder until Place details are joined)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AppText(
                    text = "Place: ${item.placeId}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 1.dp),
                    fontSize = 12.sp,
                    useGrayColor = true
                )
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)
                )
            }

            // Date + Travelers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 6.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                AppText(
                    text = "Travelers ${item.numberOfPeople}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 2.dp, start = 5.dp),
                    fontSize = 12.sp,
                    useGrayColor = true
                )
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = "Travelers",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                AppText(
                    text = "${item.bookingDate} (${item.startTime} - ${item.endTime})",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 15.dp),
                    fontSize = 12.sp,
                    useGrayColor = true
                )
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Date",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)
                )
            }

            // Status row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 6.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AppText(
                    text = "Status: ${item.status}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp, start = 5.dp),
                    fontSize = 12.sp,
                    isBold = true,
                    italic = true
                )
            }
        }
    }
}
