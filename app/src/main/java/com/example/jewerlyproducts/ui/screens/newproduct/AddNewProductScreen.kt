package com.example.jewerlyproducts.ui.screens.newproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.screens.home.SellDialog

@Composable
fun AddNewProductScreen(
    innerPadding: PaddingValues,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: AddNewProductViewModel = hiltViewModel()
) {

    val productName by viewModel.productName.collectAsState()
    val materialQuantity by viewModel.materialQuantity.collectAsState()
    val showMaterialDialog by viewModel.showMaterialDialog.collectAsState()

    ScreenBackgroundComponent(
        innerPadding = innerPadding,
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FirstTitleItem("Crear nuevo producto")
                Spacer(Modifier.size(0.dp))
                SingleLineTextFieldItem(
                    value = productName,
                    label = "Nombre del producto"
                ) { viewModel.updateProductName(it) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        SecondTitleItem("Listado de materiales")
                    }
                    TextButton(
                        onClick = { viewModel.updateShowMaterialDialog(true) }
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add material",
                            tint = Color.White
                        )
                    }
                }
                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            "Artículo",
                            modifier = Modifier.weight(.8f),
                            fontWeight = FontWeight.Bold,
                            color = Color.Cyan
                        )
                        Text(
                            "Cantidad",
                            modifier = Modifier.weight(.5f),
                            fontWeight = FontWeight.Bold,
                            color = Color.Cyan
                        )
                        Text(
                            "Costo",
                            modifier = Modifier.weight(.2f),
                            fontWeight = FontWeight.Bold,
                            color = Color.Cyan
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Corralines", modifier = Modifier.weight(.8f))
                        Text("100 u.", modifier = Modifier.weight(.5f))
                        Text("$120", modifier = Modifier.weight(.2f))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cristales de metal", modifier = Modifier.weight(.8f))
                        Text("20 gr", modifier = Modifier.weight(.5f))
                        Text("$1300", modifier = Modifier.weight(.2f))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cristales de metal", modifier = Modifier.weight(.8f))
                        Text("20 gr", modifier = Modifier.weight(.5f))
                        Text("$1300", modifier = Modifier.weight(.2f))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cristales de metal", modifier = Modifier.weight(.8f))
                        Text("20 gr", modifier = Modifier.weight(.5f))
                        Text("$1300", modifier = Modifier.weight(.2f))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cristales de metal", modifier = Modifier.weight(.8f))
                        Text("20 gr", modifier = Modifier.weight(.5f))
                        Text("$1300", modifier = Modifier.weight(.2f))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cristales de metal", modifier = Modifier.weight(.8f))
                        Text("20 gr", modifier = Modifier.weight(.5f))
                        Text("$1300", modifier = Modifier.weight(.2f))
                    }
                }
                Spacer(Modifier.size(0.dp))
                SecondTitleItem("Costo Total: $2000")
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Seleccionar foto")
                }
                Spacer(modifier = Modifier.weight(1f))
                AcceptDeclineButtons(
                    acceptText = "Agregar artículo",
                    declineText = "Cancelar",
                    onAccept = { },
                    onDecline = { onDismiss() },
                    enabled = productName.isNotBlank()
                )
                Spacer(modifier = Modifier.size(16.dp))
                SellDialog(
                    show = showMaterialDialog,
                    title = "Agregar Material",
                    quantitySell = materialQuantity,
                    onQuantitySellChange = { viewModel.updateMaterialQuantity(it) },
                    onDismiss = {
                        viewModel.updateShowMaterialDialog(false)
                        viewModel.clearMaterialStats()
                    },
                    onAccept = { }
                )
            }
        }
    }

}

