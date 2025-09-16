package com.example.jewerlyproducts.ui.screens.materialdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.ConfirmDialog
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.NumericTextField
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SimpleButtonText
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.components.TextFieldAreaItem

@Composable
fun MaterialDetailsScreen(
    innerPadding: PaddingValues,
    materialName: String,
    viewModel: MaterialDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {

    LaunchedEffect(true) {
        viewModel.getMaterialByName(materialName)
    }

    val materialDetails by viewModel.materialDetails.collectAsState()
//    val materialName by viewModel.materialName.collectAsState()
    val materialPrice by viewModel.materialPrice.collectAsState()
    val quantityPerPack by viewModel.quantityPerPack.collectAsState()
    val annotations by viewModel.annotations.collectAsState()
    val individualPrice by viewModel.individualPrice.collectAsState()

    var enabledButton by rememberSaveable { mutableStateOf(false) }

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(materialPrice, quantityPerPack, materialName) {
        if (quantityPerPack == null || materialPrice == null) enabledButton = false
        enabledButton = checkFields(materialName, quantityPerPack ?: 0, materialPrice ?: 0)
    }

    ScreenBackgroundComponent(innerPadding) {

        if (materialDetails == null) {
            CircularProgressIndicator()
            return@ScreenBackgroundComponent
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 32.dp), contentAlignment = Alignment.Center
        ) {
            FirstTitleItem("Editar material")
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
                    acceptText = "Guardar Cambios",
                    declineText = "Cancelar",
                    onDecline = { onDismiss() },
                    onAccept = {
                        // Update on Room and back
                        viewModel.updateMaterial(materialName)
                        onDismiss()
                    },
                    enabled = enabledButton
                )
                SimpleButtonText("Eliminar Material", Color.Red) {
                    showConfirmDialog = true
                }
            }
            ConfirmDialog(
                show = showConfirmDialog,
                text = "¿Estás segura que queres eliminar el material?",
                onAccept = {
                    viewModel.deleteMaterialById(materialName)
                    showConfirmDialog = false
                    onDismiss()
                },
                onDismiss = { showConfirmDialog = false }
            )
        }

    }

}

private fun checkFields(name: String, packValue: Int, unitValue: Int): Boolean {
    return (name.isNotBlank() && packValue != 0 && unitValue != 0)
}

