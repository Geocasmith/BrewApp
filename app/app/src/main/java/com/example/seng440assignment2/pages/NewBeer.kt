package com.example.seng440assignment2.pages

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.model.BeerType
import org.json.JSONObject


class EditBeerViewModel : ViewModel() {

    var beerName: String by mutableStateOf("")
    var beerType: String by mutableStateOf(BeerType.HAZY.name)
    var beerBarcode: String by mutableStateOf("")
    var beerImageURL: String by mutableStateOf("")


    fun canSave(): Boolean {
        return beerBarcode.isNotEmpty() && beerName.isNotEmpty() && beerBarcode.isNotEmpty()
    }
}

@Composable
fun NewBeer(editBeerViewModel: EditBeerViewModel = viewModel(), viewModel: MainViewModel, barcode: String?, onBeerSaved: () -> Unit) {
    editBeerViewModel.beerBarcode = barcode.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier
                .width(width = 264.dp)
        ) {
            item { HeaderText() }
            item { ImageUrlBox(editBeerViewModel) }
            item { SpacerDP(6) }
            item { BeerNameBox(editBeerViewModel) }
            item { SpacerDP(6) }
            item { BeerTypeDropDown(editBeerViewModel) }
            item { SpacerDP(6) }
            item { BarcodeBox(editBeerViewModel, !barcode.isNullOrBlank()) }
            item { SpacerDP(12) }
            item { AddButton(viewModel, editBeerViewModel, onBeerSaved) }
        }

    }

}

@Composable
private fun AddButton(viewModel: MainViewModel, editBeerViewModel: EditBeerViewModel, onBeerSaved: () -> Unit) {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .width(width = 140.dp)
            .height(height = 48.dp),
        onClick = {
            if (editBeerViewModel.canSave()) {
                val jsonRequest = JSONObject()
                jsonRequest.put("name", editBeerViewModel.beerName)
                jsonRequest.put("barcode", editBeerViewModel.beerBarcode)
                jsonRequest.put("type", editBeerViewModel.beerType)
                jsonRequest.put("photoUrl", editBeerViewModel.beerImageURL)

                val request = viewModel.postObjectRequest(context, "/beer", jsonRequest, {})
                viewModel.addRequestToQueue(request)
                onBeerSaved()
            } else {
                Toast.makeText(context, context.getText(R.string.new_cant_save), Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = "ADD",
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
private fun HeaderText() {
    Text(
        text = "Create new beer!",
        color = Color.Black.copy(alpha = 0.87f),
        lineHeight = 40.sp,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .width(width = 264.dp)
            .height(height = 48.dp)
    )
    SpacerDP(12)
    Text(
        text = "Looks like your the first one to scan this beer, add the details to review it!",
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
fun ImageUrlBox(viewModel: EditBeerViewModel) {
    OutlinedTextField(
        value = viewModel.beerImageURL,
        onValueChange = { viewModel.beerImageURL = it },
        label = { Text("Image URL", color = Color.Black.copy(alpha = 0.6f)) }
    )
}

@Composable
fun BeerNameBox(viewModel: EditBeerViewModel) {
    OutlinedTextField(
        value = viewModel.beerName,
        onValueChange = { viewModel.beerName = it },
        label = { Text("Beer Name*", color = Color.Black.copy(alpha = 0.6f)) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BeerTypeDropDown(viewModel: EditBeerViewModel) {
    //rating drop down
    val beerTypeOptions = BeerType.values().map { it.name }
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = viewModel.beerType,
            onValueChange = { },
            label = { Text("Beer Type*") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            beerTypeOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.beerType = selectionOption
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun BarcodeBox(viewModel: EditBeerViewModel, isDisabled: Boolean) {
    OutlinedTextField(
        value = viewModel.beerBarcode,
        onValueChange = { viewModel.beerBarcode = it },
        enabled = isDisabled,
        label = { Text("Barcode*", color = Color.Black.copy(alpha = 0.6f)) }
    )
}

