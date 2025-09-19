package com.aou.citysage.screens.favorite

import android.R.attr.iconTint
import android.content.res.Resources
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.wear.compose.material.Colors
import com.aou.citysage.AppText
import com.aou.citysage.FilterMenu
import com.aou.citysage.R
import com.aou.citysage.screens.Login.Place
import com.aou.citysage.screens.home.CategoryButton
import com.aou.citysage.screens.home.places

@Composable
@Preview(showSystemUi = true)
fun FavoriteScreen() {
    val scrollState = rememberScrollState()
    // Use RTL if needed (for Arabic)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                Icon(

                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "Filter",
                    // tint = Color(0xFF9C27B0),
                    modifier = Modifier.size(35.dp).padding(end = 8.dp)
                )
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("المفضلة", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("3 Saved Places ", fontSize = 14.sp, color = Color.Gray,
                        fontStyle = FontStyle.Italic, modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }

            // City Tabs (Pill Buttons)
            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                horizontalArrangement = Arrangement.Start,

                ) {
                val categories = listOf("Religon Sites", "History"," Culture" ,"All Cities")

                LazyRow(
                    // modifier = Modifier.fillMaxSize()
                ) {
                    // Use `items` to iterate over a list
                    items(categories) { item ->
                        FilterMenu(item)
                    }
                    // You can also add a single item using `item`
                    item {
                        //Text(text = "This is a footer item", modifier = Modifier.padding(16.dp))
                    }
                }

            }




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
                    FavoriteCard(it)
                }
            }

        }
    }
}

@Composable
fun FavoriteCard(item: Place) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray // Change this to your desired color
        )
    ){
        Image(
            painter = painterResource(id = R.drawable.quran),
            contentDescription = "Place Image",
        )
        Column   (
            modifier = Modifier
                .height(100.dp)
                .background(Color(0xFFF5F5F5))
                .fillMaxWidth()
        ){

            // Title
            Row (
                modifier = Modifier
                    .fillMaxWidth().padding(top = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End

            )
            {
                Text(text = "المسجد النبوي")
            }
            // Times
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End)
            {
                AppText(
                    text = "24/7",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 4.dp),
                    fontSize = 12.sp,
                    useGrayColor = true
                )
                Icon(
                    painter = painterResource(id = R.drawable.clock), // Material icon for a clock/timer
                    contentDescription = "Timer",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)

                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth().
                    padding(top = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End
            )

            {

                Text(
                    text = "Free",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(
                        start = 10.dp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                AppText(
                    text = "0.5 Km",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 1.dp),
                    fontSize = 12.sp,

                )

                Icon(
                    imageVector = Icons.Outlined.LocationOn, // Material icon for a clock/timer
                    contentDescription = "Location",
                   // tint = Color(0xFFE0D57A),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)

                )
                AppText(
                    text = "○",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp),
                    fontSize = 12.sp,
                    isBold = true
                )
                AppText(
                    text = "4.9",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 4.dp),
                    fontSize = 12.sp,
                    isBold = true
                )
                Icon(
                    imageVector = Icons.Filled.Star, // Material icon for a clock/timer
                    contentDescription = "Star",
                    tint = Color(0xFFE0D57A),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)

                )


            }
        }
    }

}
