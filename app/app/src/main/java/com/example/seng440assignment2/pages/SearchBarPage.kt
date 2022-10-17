package com.example.seng440assignment2.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.seng440assignment2.R
import kotlinx.coroutines.launch

//TODO: Implement viewmodel, seems to crash app every time I try implement it
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarPage() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var searchInput by remember { mutableStateOf("") }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                    value = searchInput,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {searchInput=it },
                        trailingIcon = {
                                IconButton(onClick = { searchInput = "" }) {
                                    Icon(Icons.Outlined.Search, contentDescription = "Search")
                                }
                        },
                ) },
//                actions = {
//                    IconButton(onClick = {/*TODO SEARCH*/}) {
//                        Icon(Icons.Outlined.Search, contentDescription = "Search")
//                    }
//                },
                //add a search bar here



                backgroundColor = Color.White
            )

        },
        content = {
        },)

}