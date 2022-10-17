package com.example.seng440assignment2.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seng440assignment2.R
import com.example.seng440assignment2.AuthViewModel

@Composable
fun Register(viewModel: AuthViewModel, onLoginLinkClicked: () -> Unit) {
    val context = LocalContext.current

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
            SignUpTitleText()
            NameBox(viewModel)
            SpacerDP(6)
            UsernameBox(viewModel)
            SpacerDP(6)
            PasswordBox(viewModel)
            SpacerDP(12)
            LoginNavigation(onLoginLinkClicked)
            SignUpButton { viewModel.signup(context, onLoginLinkClicked) }
        }
    }
}

@Composable
private fun SignUpButton(onSignUp: () -> Unit) {
    Button(
        modifier = Modifier
            .width(width = 140.dp)
            .height(height = 48.dp),
        onClick = { onSignUp() },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.signup_btn_text),
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
private fun NameBox(viewModel: AuthViewModel) {
    OutlinedTextField(
        value = viewModel.name,
        onValueChange = { viewModel.name = it },
        label = { Text(stringResource(id = R.string.signup_name_placeholder), color = Color.Black.copy(alpha = 0.6f)) }
    )
}


@Composable
private fun LoginNavigation(onPressed: () -> Unit) {
    ClickableText(
        text = AnnotatedString(text = stringResource(id = R.string.login_link)),
        style = TextStyle(textDecoration = TextDecoration.Underline, color = Color.Blue.copy(alpha = 0.54f)),
        modifier = Modifier
            .width(width = 264.dp)
            .height(height = 50.dp),
        onClick = { onPressed() }
    )
}

@Composable
private fun SignUpTitleText() {
    Text(
        text = stringResource(id = R.string.signup_title),
        color = Color.Black.copy(alpha = 0.87f),
        lineHeight = 40.sp,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .width(width = 264.dp)
            .height(height = 48.dp)
    )
    SpacerDP(12)
    Text(
        text = stringResource(id = R.string.signup_description),
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

