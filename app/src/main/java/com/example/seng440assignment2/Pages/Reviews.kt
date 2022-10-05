package com.example.seng440assignment2.Pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Tab
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Tab
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import coil.compose.AsyncImage

@Composable
fun Reviews() {
BeerCard("LOST AND GROUNDED", "A crisp and clean beer that will quench your thirst any time","Mike",4 )
//    create a list of beercards
}
@Composable
fun BeerCard(title:String, description:String, name:String,rating:Int,onClick: () -> Unit = {}) {
    val padding=16.dp
    Card(elevation = 4.dp,modifier= Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)){
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {


            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(padding)) {
                Text(
                    text = title,
                    color = Color.Black.copy(alpha = 0.87f),
                    lineHeight = 16.sp,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.5.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    color = Color.Black.copy(alpha = 0.87f),
                    lineHeight = 24.sp,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    TODO:Make the name clickable to go to the user's profile
                    Text(
                        text = name,
                        color = Color.Black.copy(alpha = 0.6f),
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row {
                        //filled star for rating and unfilled for remaining out of 5
                        for (i in 1..5) {
                            if (i <= rating) {
                                Icon(Icons.Outlined.Star, contentDescription = "Filled Star")
                            } else {
                                Icon(Icons.Outlined.Grade, contentDescription = "Unfilled Star")
                            }
                        }
                    }
                }

                AsyncImage(model = "https://cdn.shopify.com/s/files/1/0178/4982/products/O_zapftis__Render_Web.png?v=1664829695", contentDescription = "Beer!",modifier = Modifier.height(90.dp).width(50.dp))

            }
            Text("HI")
//            AsyncImage(model = "https://cdn.shopify.com/s/files/1/0178/4982/products/O_zapftis__Render_Web.png?v=1664829695", contentDescription = "Beer!",modifier = Modifier.height(90.dp).width(50.dp))

        }
    }
}