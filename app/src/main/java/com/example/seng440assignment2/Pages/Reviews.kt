package com.example.seng440assignment2.Pages
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
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

@Composable
fun Reviews() {
BeerCard()
}
@Composable
fun BeerCard() {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.3f)
    ) {
        Text(
            text = "LOST AND GROUNDED",
            color = Color.Black.copy(alpha = 0.87f),
            lineHeight = 16.sp,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.5.sp))

    }
}