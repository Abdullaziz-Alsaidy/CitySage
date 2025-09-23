package com.aou.citysage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.common.collect.Multimaps.index
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
@Composable
@Preview
fun FilterMenu(text: String= "D",index : Int=1){
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
                    Color(0xFFC0C9C3),
                    contentColor = Color.White
                )

            else -> ButtonDefaults.buttonColors(
                Color(0xFFF5F5F5),
                contentColor = Color.Gray
            )
        }
    ) {
        Text(text, fontSize = 14.sp)
    }
}





@Composable
fun AppText(
    italic: Boolean = false,
    text: String,
    fontSize: TextUnit = 16.sp, // Default size
    isBold: Boolean = false, // Default not bold
    useGrayColor: Boolean = false, // Default black color
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
        color = if (useGrayColor) Color.Gray else Color.Black,
        modifier = modifier,
        fontStyle = if(italic) FontStyle.Italic else FontStyle.Normal
    )
}

@Composable
fun GetTheRightImage(placeName: String ="جبل عرفات"){
    var imageHolder:Int =5
    if (placeName == "جبل عرفات"){
        imageHolder = R.drawable.quran
    }else{
        imageHolder = R.drawable.sample_image
    }
    Image(
        painter = painterResource(id = imageHolder),
        contentDescription = "Place Image",
       // modifier = Modifier
         //   .height(50.dp)
          //  .width(50.dp)
           // .padding(end = 8.dp)
    )
}