package com.aou.citysage.screens.home

import android.R.attr.navigationIcon
import android.content.Context
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.aou.citysage.AppText
import com.aou.citysage.GetTheRightImage
import com.aou.citysage.R
import com.aou.citysage.data.models.Constant_Place
import com.aou.citysage.data.models.Place
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
@Preview
fun PlaceDetailsScreen(
    placeID: String = "999",
    viewModel: PlaceDetailsViewModel = viewModel()
) {
    Log.d("PlaceDetailsScreen", "Received placeID: $placeID")
    val placeState by viewModel.placeState.collectAsState()

    LaunchedEffect(placeID) {
        viewModel.fetchPlace(placeID)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (placeState) {
            is PlaceState.Success -> {
                val place = (placeState as PlaceState.Success).place
                PlaceDetailsComponent(place)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview
fun PlaceDetailsComponent(place: Place = Constant_Place) {

    Scaffold(
        topBar = {
                TopAppBar(
                title = { AppText(
                    text = place.name,
                    fontSize = 18.sp,
                    isBold = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        //.padding(top = 8.dp)
                ) },
            ) }
        ,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Navigate to booking screen */ },
                icon = { Icon(Icons.Default.LocationOn, contentDescription = "Book") },
                text = { Text("Book Now") }
            )
        }
    ) { paddingValues ->
        PlaceDetailsContent(place, Modifier.padding(paddingValues))
    }
}

@Composable
fun PlaceDetailsContent(place: Place = Place(), modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        // Image Gallery
        item {
            //GetTheRightImage(place.name)
            Image(
                painter = painterResource(id = R.drawable.quran),
                contentDescription = "Place Image",
                // modifier = Modifier
                //   .height(50.dp)
                //  .width(50.dp)
                // .padding(end = 8.dp)
            )
        }

        // Basic Info Section
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Rating and Price
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                    Text(
                        text = "%.1f".format(place.rating),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 16.dp)
                    )

                    Text(
                        text = "$${place.price}/person",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Category and Duration
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Chip(
                        onClick = { },
                        label = { Text(place.category) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Chip(
                        onClick = { },
                        label = { Text(place.duration) }
                    )
                }
            }
        }

        // Details Section
        item {
            SectionTitle("About")
            Text(
                text = place.details,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Information Cards
        item {
            SectionTitle("Information")
            InformationCards(place)
        }

        // Location Section
        item {
            SectionTitle("Location")
            LocationSection(place, context)
        }

        // Add spacing for FAB
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageGallery(images: List<String>, placeName: String) {
    val pagerState = rememberPagerState { images.size }
    val state = rememberPagerState { 10 }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->
            AsyncImage(
                model = images.getOrElse(page) { "" },
                contentDescription = "$placeName image ${page + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Dots indicator if multiple images
        if (images.size > 1) {

        }
    }
}

@Composable
fun InformationCards(place: Place) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InfoCard(
            icon = Icons.Default.Search,
            title = "Opening Hours",
            content = place.openTimes
        )

        InfoCard(
            icon = Icons.Default.Search,
            title = "Capacity",
            content = "Up to ${place.capacity} people"
        )

        InfoCard(
            icon = Icons.Default.LocationOn,
            title = "Address",
            content = place.address
        )

        if (place.phone.isNotEmpty()) {
            InfoCard(
                icon = Icons.Default.Phone,
                title = "Contact",
                content = place.phone
            )
        }
    }
}

@Composable
fun InfoCard(icon: ImageVector, title: String, content: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun LocationSection(place: Place, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Simple map placeholder - you can integrate with Google Maps later
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Map View - ${place.address}")
        }
    }

    // Action buttons for location
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedButton(
            onClick = { /* Open in Maps */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "Open in Maps")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Open in Maps")
        }

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedButton(
            onClick = { /* Share location */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Share, contentDescription = "Share")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Share")
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun Chip(onClick: () -> Unit, label: @Composable () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            label()
        }
    }
}
