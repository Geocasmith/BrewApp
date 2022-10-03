package com.example.seng440assignment2.Pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Login() {
    Column(
        modifier = Modifier
            .width(width = 264.dp)
    ) {
        Text(
            text = "Let’s get started!",
            color = Color.Black.copy(alpha = 0.87f),
            lineHeight = 40.sp,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .width(width = 264.dp)
                .height(height = 48.dp))
        Spacer(
            modifier = Modifier
                .height(height = 12.dp))
        Text(
            text = "Login to access your bookmarks and personal preferences.",
            color = Color.Black.copy(alpha = 0.54f),
            lineHeight = 24.sp,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .width(width = 264.dp)
                .height(height = 50.dp))
        Spacer(
            modifier = Modifier
                .height(height = 12.dp))
        UsernameBox()
        Spacer(
            modifier = Modifier
                .height(height = 12.dp))
        PasswordBox()
        Spacer(
            modifier = Modifier
                .height(height = 12.dp))
        Spacer(
            modifier = Modifier
                .height(height = 12.dp))
        Button(onClick = { /* Do something! */ }, colors = ButtonDefaults.textButtonColors(
            Color.Blue
        )) {
            Text(
                text = "LOGIN",
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp),
                modifier = Modifier
                    .width(width = 96.dp))
        }

    }

}

@Composable
fun UsernameBox() {
    var text by remember { mutableStateOf<String>("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { "Label" }
    )
}

@Composable
fun PasswordBox() {
    var text by remember { mutableStateOf<String>("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { "Label" },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

