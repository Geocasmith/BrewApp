package com.example.seng440assignment2.Pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Login() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .width(width = 264.dp)
        ) {
            TitleText()
            UsernameBox()
            SpacerDP(6)
            PasswordBox()
            SpacerDP(12)
            LoginNavigation()
            LoginButton()
        }

    }

}

@Composable
private fun LoginButton() {
    Button(
        modifier = Modifier
            .width(width = 140.dp)
            .height(height = 48.dp),
        onClick = { /* Do something! */ },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = "LOGIN",
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            ),
            modifier = Modifier
                .width(width = 96.dp)
        )
    }
}


@Composable
private fun LoginNavigation() {
    Text(
        text = "Have an account? Log in",
        color = Color.Blue.copy(alpha = 0.54f),
        lineHeight = 24.sp,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        modifier = Modifier
            .width(width = 264.dp)
            .height(height = 50.dp)
    )
}

@Composable
private fun TitleText() {
    Text(
        text = "Let’s get started!",
        color = Color.Black.copy(alpha = 0.87f),
        lineHeight = 40.sp,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .width(width = 264.dp)
            .height(height = 48.dp)
    )
    SpacerDP(12)
    Text(
        text = "Login to access your reviews and profile.",
        color = Color.Black.copy(alpha = 0.54f),
        lineHeight = 24.sp,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .width(width = 264.dp)
            .height(height = 50.dp)
    )
}

@Composable
private fun SpacerDP(DPSpacer: Int) {
    Spacer(
        modifier = Modifier
            .height(height = DPSpacer.dp)
    )
}

@Composable
fun UsernameBox() {
    var text by remember { mutableStateOf<String>("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Username", color = Color.Black.copy(alpha = 0.6f)) }
    )
}

@Composable
fun PasswordBox() {
    var text by remember { mutableStateOf<String>("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Password", color = Color.Black.copy(alpha = 0.6f)) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}
