package com.example.seng440assignment2.Pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Tab
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Tab
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SignUp() {
    LazyColumn(
        modifier = Modifier
            .height(height = 800.dp)
    ) {
        item {
            ActionBarWhite()
        }

        item {
            SignUpFields()
        }

    }
}

@Composable
fun ActionBarWhite() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .size(size = 40.dp)
        ) {
            Icon(Icons.Outlined.ArrowBack, contentDescription = null)
        }
        Spacer(
            modifier = Modifier
                .width(width = 224.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = { },
                modifier = Modifier
                    .height(height = 40.dp)
            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.star-outline),
//                    contentDescription = "Icon",
//                    tint = Color.Black.copy(alpha = 0.87f))
            }
            FloatingActionButton(
                onClick = { },
                modifier = Modifier
                    .height(height = 40.dp)
            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.more),
//                    contentDescription = "Icon",
//                    tint = Color.Black.copy(alpha = 0.87f))
            }
        }
    }
}

@Composable
fun SignUpFields() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Title()
        Spacer(
            modifier = Modifier
                .height(height = 16.dp))
        ChooseSubtitle()
        Spacer(
            modifier = Modifier
                .height(height = 16.dp))
        TextFieldOutlined()
        Spacer(
            modifier = Modifier
                .height(height = 16.dp))
        TextFieldOutlined()
        Spacer(
            modifier = Modifier
                .height(height = 16.dp))
//        Tab(
//            selected = false,
//            onClick = {  },
//            text = {
//                Text(
//                    text = "Sign up",
//                    color = Color.White,
//                    textAlign = TextAlign.Center,
//                    lineHeight = 16.sp,
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Medium,
//                        letterSpacing = 1.sp),
//                    modifier = Modifier
//                        .width(width = 296.dp))
//            })
    }
}

@Composable
fun Title() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .width(width = 328.dp)
    ) {
        Text(
            text = "Sign up",
            color = Color.Black.copy(alpha = 0.87f),
            style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ChooseSubtitle() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .width(width = 328.dp)
    ) {
        Text(
            text = "Welcome to BeerHawk",
            color = Color.Black.copy(alpha = 0.6f),
            lineHeight = 24.sp,
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun TextFieldOutlined() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .width(width = 328.dp)
                .height(height = 48.dp))
        Text(
            text = "Password",
            color = Color(0xff1554f6),
            lineHeight = 16.sp,
            style = MaterialTheme.typography.bodySmall)
        Text(
            text = "••••••••",
            color = Color.Black.copy(alpha = 0.6f),
            lineHeight = 28.sp,
            style = TextStyle(
                fontSize = 16.sp,
                letterSpacing = 0.44.sp),
            modifier = Modifier
                .width(width = 242.dp)
                .height(height = 40.dp))
//        Icon(
//            painter = painterResource(id = R.drawable.visibility),
//            contentDescription = "icon",
//            tint = Color.Black.copy(alpha = 0.6f))
    }
}