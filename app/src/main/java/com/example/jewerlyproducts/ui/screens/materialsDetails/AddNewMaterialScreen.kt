package com.example.jewerlyproducts.ui.screens.materialsDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.NumericTextField
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.components.TextFieldAreaItem

@Composable
fun AddNewMaterialScreen(
    innerPadding: PaddingValues,
    onDismiss:() -> Unit,
    onAddMaterial:() -> Unit,
    viewModel: AddNewMaterialViewModel = hiltViewModel()
) {

    val materialName by viewModel.name.collectAsState()
    val materialPrice by viewModel.price.collectAsState()
    val quantityPerPack by viewModel.quantityPerPack.collectAsState()
    val individualPrice by viewModel.individualPrice.collectAsState()
    val annotations by viewModel.annotations.collectAsState()
    var enabledButton by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(materialPrice, quantityPerPack, materialName) {
        if (quantityPerPack == null || materialPrice == null) enabledButton = false
        enabledButton = checkFields(materialName, quantityPerPack ?: 0, materialPrice ?: 0)
    }

    ScreenBackgroundComponent(innerPadding) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 32.dp), contentAlignment = Alignment.Center
        ) {
            FirstTitleItem("Agregar nuevo material")
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SingleLineTextFieldItem(
                    value = materialName,
                    label = "Nombre"
                ) { viewModel.updateName(it) }
                NumericTextField(
                    value = if (materialPrice == null) "" else materialPrice.toString(),
                    label = "Precio por paquete"
                ) {
                    viewModel.updatePrice(it)
                }
                NumericTextField(
                    value = if (quantityPerPack == null) "" else quantityPerPack.toString(),
                    label = "Unidades por paquete"
                ) {
                    viewModel.updateQuantityPerPack(it)
                }
                TextFieldAreaItem(
                    annotations,
                    "Anotaciones"
                ) { viewModel.updateAnnotations(it) }
                if (individualPrice != null) {
                    BodyText("Precio por unidad: $individualPrice")
                }
                Spacer(Modifier.size(32.dp))
                AcceptDeclineButtons(
                    acceptText = "Agregar Material",
                    declineText = "Cancelar",
                    onDecline = { onDismiss() },
                    onAccept = {
                        // Add on Room and back
                    },
                    enabled = enabledButton
                )
            }
        }

    }
}

private fun checkFields(name: String, packValue: Int, unitValue: Int): Boolean {
    return (name.isNotBlank() && packValue != 0 && unitValue != 0)
}


