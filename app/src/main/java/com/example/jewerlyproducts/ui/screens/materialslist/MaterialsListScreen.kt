package com.example.jewerlyproducts.ui.screens.materialslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.FloatingButton
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.theme.Purple40

@Composable
fun MaterialsListScreen(innerPadding: PaddingValues,onNavigateToAddNewMaterial:() -> Unit, onNavigateToMaterialDetails:(Int) -> Unit, viewModel: MaterialsListViewModel = hiltViewModel()) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val materialsList by viewModel.materialsList.collectAsState()

    ScreenBackgroundComponent(innerPadding) {
        FirstTitleItem("Listado de materiales")
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.updateQuery(it) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray.copy(.2f),
                unfocusedContainerColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por nombre..") },
            trailingIcon = {
                if(searchQuery.isNotBlank()) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "clear data",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.updateQuery("")
                        }
                    )
                }
            }

        )
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if(materialsList.isNotEmpty()) {
                materialsList.forEach {
                    MaterialItem(
                        name = it.name,
                        unitPrice = it.unitPrice,
                        quantityPerPack = it.quantityPerPack
                    ) {
                        onNavigateToMaterialDetails(it.id)
                    }
                }
            } else {
                SecondTitleItem("No hay nada para mostrar")
            }

        }
    }
    FloatingButton(innerPadding) { onNavigateToAddNewMaterial() }
}

@Composable
fun MaterialItem(name:String, unitPrice:Int, quantityPerPack:Int, onClick:() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
        ,
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Purple40
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                SecondTitleItem(name)
                HorizontalDivider(Modifier.fillMaxWidth(), thickness = 2.dp, color = Color.White)
            }
            BodyText("Precio por unidad: $$unitPrice")
            BodyText("Cantidad por pack: $quantityPerPack")
        }
    }
}