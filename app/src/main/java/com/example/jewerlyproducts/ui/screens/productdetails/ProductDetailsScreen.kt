package com.example.jewerlyproducts.ui.screens.productdetails

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jewerlyproducts.R
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.ConfirmDialog
import com.example.jewerlyproducts.ui.components.DialogWithListAndQuantity
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.NumericTextField
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.components.SimpleButtonText
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass

@Composable
fun ProductDetailsScreen(
    innerPadding: PaddingValues,
    productId: Int,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {

    LaunchedEffect(true) {
        viewModel.getProductById(productId)
    }

    val productDetails by viewModel.productDetails.collectAsState()
    val productName by viewModel.productName.collectAsState()
    val showMaterialDialog by viewModel.showMaterialDialog.collectAsState()
    val costValue by viewModel.costValue.collectAsState()
    val sellValue by viewModel.sellValue.collectAsState()
    val materialsList by viewModel.materialsList.collectAsState()
    val materialQuantity by viewModel.materialQuantity.collectAsState()
    val productImageUri by viewModel.productImageUri.collectAsState()

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            viewModel.updateImageUri(uri.toString())
        }
    )

    ScreenBackgroundComponent(
        innerPadding = innerPadding,
    ) {
        if (productDetails == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@ScreenBackgroundComponent
        }

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FirstTitleItem("Editar producto")
                    SimpleButtonText(
                        text = "Eliminar",
                        color = Color.Red
                    ) { showConfirmDialog = true }
                }
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
                }
                Spacer(Modifier.size(0.dp))
                SecondTitleItem("Costo Total: $$costValue")
                NumericTextField(
                    value = sellValue.ifBlank { "" },
                    label = "Valor de venta"
                ) { viewModel.updateSellValue(it) }
                Button(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Seleccionar foto")
                }
                if (productImageUri.isNotBlank()) {
                    Box(Modifier.size(150.dp), contentAlignment = Alignment.TopEnd) {
                        AsyncImage(
                            model = productImageUri.toUri(),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.image_loading),
                            error = painterResource(R.drawable.image_error)
                        )
                        Button(
                            onClick = { viewModel.updateImageUri("") },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Icon(Icons.Filled.Clear, contentDescription = "", tint = Color.White)
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                AcceptDeclineButtons(
                    acceptText = "Modificar producto",
                    declineText = "Cancelar",
                    onAccept = {
                        viewModel.updateProduct(
                            ProductsDataClass(
                                id = productId,
                                name = productName,
                                cost = costValue.toInt(),
                                sellValue = sellValue.toInt(),
                                imageUri = productImageUri,
                            )
                        )
                        onDismiss()
                    },
                    onDecline = { onDismiss() },
                    enabled = productName.isNotBlank() && sellValue.isNotBlank()
                )
                Spacer(modifier = Modifier.size(16.dp))
                DialogWithListAndQuantity(
                    show = showMaterialDialog,
                    title = "Agregar Material",
                    itemList = materialsList,
                    quantitySell = materialQuantity,
                    onQuantitySellChange = { viewModel.updateMaterialQuantity(it) },
                    onDismiss = {
                        viewModel.updateShowMaterialDialog(false)
                        viewModel.clearMaterialStats()
                    },
                    onAccept = { }
                )
                ConfirmDialog(
                    show = showConfirmDialog,
                    text = "Segura que queres eliminar el producto?",
                    onAccept = {
                        viewModel.deleteProduct(productId)
                        showConfirmDialog = false
                        onDismiss()
                    }
                ) { showConfirmDialog = false }
            }
        }
    }
}