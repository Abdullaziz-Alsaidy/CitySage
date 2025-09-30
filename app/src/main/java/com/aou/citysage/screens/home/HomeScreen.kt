package com.aou.citysage.screens.home

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aou.citysage.AppText
import com.aou.citysage.GetTheRightImage
import com.aou.citysage.R
import com.aou.citysage.data.models.Place

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = HomeViewModel(),
    ToDetailsPage: (String) -> Unit
) {
    val placesState by viewModel.placesState.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .fillMaxHeight()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Header
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {

                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF7857B2))
                ) {
                    Text("EN", fontSize = 18.sp, color = Color.Black, fontStyle = FontStyle.Italic)
                }
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("CitySage", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Discover Saudi Arabia",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // City Tabs (Pill Buttons)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.End
            )
            {
                val cities = when (placesState) {
                    is PlacesState.Success -> {
                        val places = (placesState as PlacesState.Success).places
                        (places.map { it.city }.distinct() + "All Cities").reversed()
                    }
                    else -> listOf("Jeddah", "Makkah", "All Cities")
                }
                cities.forEachIndexed { index, city ->
                    Button(
                        onClick = { /* TODO: Implement city filter logic */ },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = when (city) {
                            "Makkah" -> ButtonDefaults.buttonColors(
                                Color(0xFF9C27B0),
                                contentColor = Color.White
                            )
                            else -> ButtonDefaults.buttonColors(
                                Color(0xFFF5F5F5),
                                contentColor = Color.Gray
                            )
                        }
                    ) {
                        Text(city, fontSize = 14.sp)
                    }
                }
            }

            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .height(50.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "Filter",
                    tint = Color(0xFF9C27B0),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                TextField(
                    value = "",
                    onValueChange = { /* TODO: Implement search logic */ },
                    placeholder = { Text("Search") },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 12.sp),
                    singleLine = true,
                    maxLines = 1,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }

            // Category Buttons (Icons + Text)
            when (placesState) {
                is PlacesState.Success -> {
                    val places = (placesState as PlacesState.Success).places


                    LazyRow {
                        items(places) { place ->
                            CategoryButton(place.name)
                        }
                    }
                }
                is PlacesState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is PlacesState.Error -> {
                    Text(
                        text = (placesState as PlacesState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }

            // All Places Section
            Text(
                text = "المنشآت (${when (placesState) {
                    is PlacesState.Success -> (placesState as PlacesState.Success).places.size
                    else -> 0
                }})",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.End
            )

            when (placesState) {
                is PlacesState.Success -> {
                    val places = (placesState as PlacesState.Success).places
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        places.forEach { place ->
                            PlaceCard(
                                item = place,
                                onBookClick = {
                                   // viewModel.fetchPlace(place.id)
                                    ToDetailsPage(place.id)
                                    Log.d("PlaceDetailsScreen", "Received placeID: ${place.id}")
                                }
                            )
                        }
                    }
                }
                is PlacesState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is PlacesState.Error -> {
                    Text(
                        text = (placesState as PlacesState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun PlaceCard(item: Place, onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 8.dp,),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray // Change this to your desired color

        ), onClick = onBookClick
    ){
        GetTheRightImage(item.name)
        Column   (
            modifier = Modifier
                .height(140.dp)
                .background(Color(0xFFF5F5F5))
                .fillMaxWidth()
        ){

            // Title
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End

            )
            {
                AppText(
                    text = item.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 4.dp),
                    fontSize = 20.sp,
                    isBold = true
                )
            }
            // Location Row
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                horizontalArrangement = Arrangement.End
            )
            {
                AppText(
                    text = "Jeddah, Saudi Arabia",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 1.dp),
                    fontSize = 12.sp,
                    useGrayColor = true

                )
                Icon(
                    imageVector = Icons.Outlined.LocationOn, // Material icon for a clock/timer
                    contentDescription = "Location",
                    // tint = Color(0xFFE0D57A),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)

                )

            }

            // Date Row
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 6.dp),
                horizontalArrangement = Arrangement.End,

                ){
                //   Spacer(modifier = Modifier.weight(1f))
                AppText(
                    text = "Travelers 2",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 2.dp, start = 5.dp),
                    fontSize = 12.sp,
                    useGrayColor = true
                )
                Icon(
                    imageVector = Icons.Outlined.Face, // Material icon for a clock/timer
                    contentDescription = "Location",
                    // tint = Color(0xFFE0D57A),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)

                )
                Spacer(modifier = Modifier.weight(1f))
                AppText(
                    text = "Dec 15 - Dec 18",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 15.dp),
                    fontSize = 12.sp,
                    useGrayColor = true
                )
                Icon(
                    imageVector = Icons.Outlined.DateRange, // Material icon for a clock/timer
                    contentDescription = "Location",
                    // tint = Color(0xFFE0D57A),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)

                )
            }
            // Activity Planned
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 6.dp),
                horizontalArrangement = Arrangement.End,

                ){
                IconButton (
                    onClick = {},
                    // tint = Color(0xFFE0D57A),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(start = 8.dp)
                ){
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Settings"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                AppText(
                    text = "8 Activity Planned",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp, start = 5.dp, top = 10.dp, bottom = 5.dp),
                    fontSize = 12.sp,
                    isBold = true,
                    italic = true
                    // useGrayColor = true
                )

            }



        }
    }

}


@Composable
fun CategoryButton(text: String) {
    Column(
        modifier = Modifier
            .size(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.museum),
            contentDescription = "Category Image",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun Places(item: Place, onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GetTheRightImage(item.name)

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = item.details, fontSize = 12.sp, color = Color.Gray)
                Text(text = "Open: ${item.openTimes}", fontSize = 12.sp)
                Text(text = "Rating: ${item.rating}", fontSize = 12.sp)
                Text(text = "Distance: ${item.distance}", fontSize = 12.sp)
                Text(text = "Price: ${item.price}", fontSize = 12.sp)
            }
            Button(
                onClick = onBookClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF9C27B0))
            ) {
                Text("Book", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}