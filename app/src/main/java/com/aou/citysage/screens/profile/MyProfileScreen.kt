package com.aou.citysage.screens.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aou.citysage.AppText
import com.aou.citysage.R


@Composable
@Preview(showSystemUi = true)
fun MyProfileScreen() {
    val scrollState = rememberScrollState()
    val signedIn = remember { true }
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
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    AppText(
                        text = "الملف الشخصي",
                        fontSize = 35.sp,
                        isBold = true,
                        italic = true

                    )

                }
            }

            // If logged IN
            if (signedIn){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 8.dp)//.height(140.dp),
                   , shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray // Change this to your desired color
                    )
                )
                {
                    Column   (
                        modifier = Modifier
                            .height(280.dp)
                            .background(Color(0xFFF5F5F5))
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally // Centers horizontally
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.guesticon),
                            contentDescription = "Guest Icon",
                            modifier = Modifier.size(100.dp)
                        )
                        AppText(
                            text = "Welcome to CitySage",
                            modifier = Modifier
                                .padding(end = 1.dp, top = 15.dp),
                            fontSize = 18.sp,
                            isBold = true,
                            italic = true

                            )
                        Text(
                            text = "Sign in to save favorites, Create trips, and get personalized recommendations",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 10,
                            modifier = Modifier
                                .padding(end = 1.dp, top = 15.dp),

                        )
                        Button(
                            modifier = Modifier
                                .padding(end = 1.dp, top = 25.dp),
                            onClick = {
                                // go to log in
                            },
                        ) {
                            AppText(text = "Sign in")

                        }

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            )
            {
                AppText(
                    text = "الاعدادات",
                    isBold = true,
                    fontSize = 24.sp
                )
            }

            // Setting
            Row (){
                SettingItem(
                    title = "اللغة",
                    subtitle = "العربية",
                    icon = painterResource(id = R.drawable.guesticon), // Replace with your globe icon
                    isChecked = true,
                    onCheckedChange = {}
                )
            }
            SettingItem(
                title = "العملة",
                subtitle = "SAR - Saudi Riyal",
                icon = painterResource(id = R.drawable.guesticon), // Replace with your credit card icon
                isChecked = false,
                onCheckedChange = {}
            )

            // Notifications Toggle
            SettingItem(
                title = "إشعارات الدفع",
                subtitle = "Push notifications & alerts",
                icon = painterResource(id = R.drawable.guesticon), // Replace with your bell icon
                isChecked = false,
                onCheckedChange = {}
            )

        }
    }
}
@Composable
fun SettingItem(
    title: String,
    subtitle: String? = null,
    icon: Painter,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Blue,
                        uncheckedThumbColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(16.dp))
                Column (
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                ){
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                }
                Image(
                    painter = painterResource(id = R.drawable.guesticon),
                    contentDescription = "Guest Icon",
                    //modifier = Modifier.size(100.dp)
                )
            }

        }
    }
}