package com.aou.citysage.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.aou.citysage.R
import com.aou.citysage.screens.Login.Place


@Composable
@Preview(showSystemUi = true)
fun HomeScreen() {
    val scrollState = rememberScrollState()
    // Use RTL if needed (for Arabic)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .fillMaxHeight()
                .verticalScroll(scrollState)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            // App Header
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {},
                    //modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF7857B2)
                    )
                ) {
                    Text("EN", fontSize = 18.sp, color = Color.Black,fontStyle = FontStyle.Italic)
                }
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("CitySage", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Discover Saudi Arabia", fontSize = 14.sp, color = Color.Gray,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // City Tabs (Pill Buttons)
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                horizontalArrangement = Arrangement.End,

            ) {
                val cities = listOf("Jeddah", "Makkah", "All Cities")
                cities.forEachIndexed { index, city ->
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .padding(vertical = 8.dp),
                            //.width(80.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = when (index) {
                            1 ->
                                ButtonDefaults.buttonColors(
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
                    .padding(vertical = 2.dp).height(50.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                   // imageVector = Icons.Default.Search,

                    painter = painterResource(id = R.drawable.filter_icon),

                    contentDescription = "Filter",
                    tint = Color(0xFF9C27B0),
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                TextField(
                    value = "",
                    onValueChange = {},
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
                    },

                )
            }

            // Category Buttons (Icons + Text)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val list = listOf(
                    Place(name = "name 1 ", icon = "icon 1 ", details = "details 1 "),
                    Place(name = "name 2 ", icon = "icon 2 ", details = "details 2 "),
                    Place(name = "name 3 ", icon = "icon 3 ", details = "details 3 "),
                    Place(name = "name 4 ", icon = "icon 4 ", details = "details 4 "),
                    Place(name = "name 1 ", icon = "icon 1 ", details = "details 1 "),
                    Place(name = "name 2 ", icon = "icon 2 ", details = "details 2 "),
                    Place(name = "name 1 ", icon = "icon 1 ", details = "details 1 "),
                    Place(name = "name 2 ", icon = "icon 2 ", details = "details 2 "),
                )
                LazyRow(
                   // modifier = Modifier.fillMaxSize()
                ) {
                    // Use `items` to iterate over a list
                    items(list) { item ->
                        CategoryButton(item.name)
                    }
                    // You can also add a single item using `item`
                    item {
                        //Text(text = "This is a footer item", modifier = Modifier.padding(16.dp))
                    }
                }
            }

            // All Places Section
            Text(
                text = "المنشآت (7)",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
                textAlign = TextAlign.End
            )
            val list = listOf(
                Place(name = "name 1 ", icon = "icon 1 ", details = "details 1 "),
                Place(name = "name 2 ", icon = "icon 2 ", details = "details 2 "),
                Place(name = "name 3 ", icon = "icon 3 ", details = "details 3 "),
                Place(name = "name 4 ", icon = "icon 4 ", details = "details 4 "),
                Place(name = "name 1 ", icon = "icon 1 ", details = "details 1 "),
                Place(name = "name 2 ", icon = "icon 2 ", details = "details 2 "),
                Place(name = "name 3 ", icon = "icon 3 ", details = "details 3 "),
                Place(name = "name 4 ", icon = "icon 4 ", details = "details 4 "),
                Place(name = "name 1 ", icon = "icon 1 ", details = "details 1 "),
                Place(name = "name 2 ", icon = "icon 2 ", details = "details 2 "),
                Place(name = "name 3 ", icon = "icon 3 ", details = "details 3 "),
                Place(name = "name 4 ", icon = "icon 4 ", details = "details 4 "),
            )



                Column  (
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        
                )
                {
                    list.forEach {
                        places(it)
                    }
                }



            // Bottom Navigation Bar
//            BottomNavigation(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                backgroundColor = Color.White,
//                elevation = 8.dp
//            ) {
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
//                    label = { Text("اكتشف") },
//                    selected = true,
//                    onClick = {}
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
//                    label = { Text("المفضلة") },
//                    selected = false,
//                    onClick = {}
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Trips") },
//                    label = { Text("رحلاتي") },
//                    selected = false,
//                    onClick = {}
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
//                    label = { Text("الخريطة") },
//                    selected = false,
//                    onClick = {}
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
//                    label = { Text("الملف الشخصي") },
//                    selected = false,
//                    onClick = {}
//                )
//            }
            }
        }
    }

@Composable
fun places(item: Place) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray // Change this to your desired color
        )
    ){
        Image(
            painter = painterResource(id = R.drawable.sample_image),
            contentDescription = "Place Image",
            modifier = Modifier.height(50.dp).width(50.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)//.weight(2f),
        )
        Text(text = item.name)
    }

}

// Reusable Category Button Component
    @Composable
    fun CategoryButton(text: String) {
        Column(
            modifier = Modifier
                //.padding(8.dp)
                .size(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.museum),
                contentDescription = "Place Image",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp)//.weight(2f),
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

