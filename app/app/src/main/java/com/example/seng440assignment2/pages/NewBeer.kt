    package com.example.seng440assignment2.pages

    import androidx.compose.foundation.ExperimentalFoundationApi
    import androidx.compose.material.*
    import androidx.compose.material3.Button
    import androidx.compose.material3.ExperimentalMaterial3Api
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
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.seng440assignment2.model.BeerType


    class EditBeerViewModel: ViewModel() {

        var beerName: String by mutableStateOf("")
        var beerType: String by mutableStateOf("Hazy")
        var beerBarcode: String by mutableStateOf("")
        var beerImageURL: String by mutableStateOf("")


        fun save() {
            /* TODO: call database */
        }
    }



    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun NewBeer(viewModel: EditBeerViewModel = viewModel(), barcode: String?) {
        var imageURL by remember { mutableStateOf<String>("") }
        var beerName by remember { mutableStateOf<String>("") }
        var barcodeInput by remember { mutableStateOf<String>("") }

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
                item {HeaderText()}
                item {ImageUrlBox(viewModel)}
                item {SpacerDP(6)}
                item {NameBox(viewModel)}
                item {SpacerDP(6)}
                item {BeerTypeDropDown(viewModel)}
                item {SpacerDP(6)}
                if (barcode != null) {
                    item {BarcodeBox(barcode)}
                }else{
                    item {BarcodeBoxWithInput(viewModel)}
                }
                item {SpacerDP(12)}
                item {AddButton()}
            }

        }

    }

    @Composable
    private fun AddButton() {
        Button(
            modifier = Modifier
                .width(width = 140.dp)
                .height(height = 48.dp),
            onClick = { /* Do something! */ },
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
    fun NameBox(viewModel: EditBeerViewModel) {
        OutlinedTextField(
            value = viewModel.beerName,
            onValueChange = { viewModel.beerName = it },
            label = { Text("Beer Name", color = Color.Black.copy(alpha = 0.6f)) }
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
                label = { Text("Beer Type") },
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
    fun BarcodeBox(barcode:String) {
        var text by remember { mutableStateOf<String>(barcode) }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            readOnly = true,
            label = { Text("Barcode", color = Color.Black.copy(alpha = 0.6f)) }
        )
    }

    @Composable
            /**
             * Barcodebox when no barcode is passed in from the navigation, lets the user input
             */
    fun BarcodeBoxWithInput(viewModel: EditBeerViewModel) {

        OutlinedTextField(
            value = viewModel.beerBarcode,
            onValueChange = { viewModel.beerBarcode = it },
            label = { Text("Barcode", color = Color.Black.copy(alpha = 0.6f)) }
        )
    }