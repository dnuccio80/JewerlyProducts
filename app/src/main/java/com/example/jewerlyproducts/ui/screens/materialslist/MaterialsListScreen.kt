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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
fun MaterialsListScreen(innerPadding: PaddingValues,onNavigateToAddNewMaterial:() -> Unit, viewModel: MaterialsListViewModel = hiltViewModel()) {

    val listName by viewModel.searchName.collectAsState()

    ScreenBackgroundComponent(innerPadding) {
        FirstTitleItem("Listado de materiales")
        TextField(
            value = listName,
            onValueChange = { viewModel.updateSearchName(it) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray.copy(.2f),
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por nombre..") },
            trailingIcon = {
                if(listName.isNotBlank()) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "clear data",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.updateSearchName("")
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
            MaterialItem()
            MaterialItem()
            MaterialItem()
            MaterialItem()
            MaterialItem()
            MaterialItem()
            MaterialItem()
            MaterialItem()
        }
    }
    FloatingButton(innerPadding) { onNavigateToAddNewMaterial() }
}

@Composable
fun MaterialItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
            SecondTitleItem("Perlas de metal")
            BodyText("Precio por unidad: $40")
            BodyText("Cantidad por bolsa: 40gr - 350 un.")
        }
    }
}