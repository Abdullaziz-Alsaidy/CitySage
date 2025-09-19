package com.aou.citysage.screens.mytrips

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aou.citysage.AppText
import com.aou.citysage.FilterMenu
import com.aou.citysage.R
import com.aou.citysage.screens.Login.Place
import com.aou.citysage.screens.favorite.FavoriteCard



@Composable
@Preview(showSystemUi = true)
fun MyTripsScreen() {
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
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )

                ) {

                    Text(text = "New Trip  ")
                    Text(text = "+",fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("رحلاتي", fontSize = 45.sp, fontWeight = FontWeight.Bold)
                    Text("3 Saved Places ", fontSize = 14.sp, color = Color.Gray,
                        fontStyle = FontStyle.Italic, modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }

            // City Tabs (Pill Buttons)
            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                horizontalArrangement = Arrangement.Center,

                ) {
                val categories = listOf("Today", "Past Trips"," Upcoming" )

                categories.forEach {
                    FilterMenu(text = it)
                }

            }




            val list = listOf(
                Place(name = "رحله نهايه الاسبوع لجده", icon = "icon 1 ", details = "details 1 "),
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
                    MyTripsCard(it)
                }
            }

        }
    }
}

@Composable
fun MyTripsCard(item: Place) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 8.dp,),
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
                .height(140.dp)
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
                    .fillMaxWidth().
                    padding(end = 5.dp),
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
                    .fillMaxWidth().
                    padding(top = 2.dp, end = 6.dp),
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
                    .fillMaxWidth().
                    padding(top = 2.dp, end = 6.dp),
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