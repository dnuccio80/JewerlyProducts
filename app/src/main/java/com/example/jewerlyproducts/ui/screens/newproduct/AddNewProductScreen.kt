package com.example.jewerlyproducts.ui.screens.newproduct

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.jewerlyproducts.R
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.ConfirmDialog
import com.example.jewerlyproducts.ui.components.DialogWithListAndQuantity
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.NumericTextField
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.components.SimpleButtonText
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.dataclasses.MaterialInProductWithQuantityDataClass
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass
import com.example.jewerlyproducts.ui.dataclasses.MaterialsSaver
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import com.example.jewerlyproducts.ui.theme.Purple40
import kotlin.math.ceil

@Composable
fun AddNewProductScreen(
    innerPadding: PaddingValues,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: AddNewProductViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val productName by viewModel.productName.collectAsState()
    val materialQuantity by viewModel.materialQuantity.collectAsState()
    val showMaterialDialog by viewModel.showMaterialDialog.collectAsState()
    val materialsList by viewModel.materialsList.collectAsState()
    val sellValue by viewModel.sellValue.collectAsState()
    val costValue by viewModel.costValue.collectAsState()
    val productImageUri by viewModel.productImageUri.collectAsState()
    val materialsInProduct by viewModel.materialsInProduct.collectAsState()

    var showEditMaterialDialog by rememberSaveable { mutableStateOf(false) }
    var materialToEdit by rememberSaveable(saver = MaterialsSaver) {
        mutableStateOf(
            MaterialsDataClass("", 0, 0, "")
        )
    }
    var materialQuantityToEdit by rememberSaveable { mutableStateOf("") }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                viewModel.updateImageUri(uri.toString())
            }
        }
    )


    ScreenBackgroundComponent(
        innerPadding = innerPadding,
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    if (materialsInProduct.isEmpty()) {
                        BodyText("Agrega los materiales que lleva el producto")
                    } else {
                        materialsInProduct.forEach {
                            MaterialsInProductRowItem(it) { materialsInProduct ->
                                materialToEdit = materialsInProduct.material
                                materialQuantityToEdit = materialsInProduct.quantity.toString()
                                showEditMaterialDialog = true
                            }
                        }
                    }
                }
                Spacer(Modifier.size(0.dp))
                SecondTitleItem("Costo Total: $$costValue")
                NumericTextField(
                    value = if (sellValue == null) "" else sellValue.toString(),
                    label = "Valor de venta"
                ) { viewModel.updateSellValue(it) }
                Button(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (productImageUri.isNotBlank()) Color.Green else Color.DarkGray
                    )
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
                    acceptText = "Agregar producto",
                    declineText = "Cancelar",
                    onAccept = {
                        if (viewModel.isSellValueGreaterThanCostValue()) {
                            viewModel.addNewProduct(
                                ProductsDataClass(
                                    productName = productName,
                                    sellValue = sellValue ?: 0,
                                    imageUri = productImageUri
                                ),
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(
                                context,
                                "El valor de venta tiene que ser superior a el costo del producto!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onDecline = { onDismiss() },
                    enabled = productName.isNotBlank() && sellValue != null
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
                    onAccept = { material, quantity ->
                        viewModel.addMaterialInProduct(
                            MaterialInProductWithQuantityDataClass(
                                material = material,
                                quantity = quantity
                            )
                        )
                    }
                )
                EditMaterialInProductDialog(
                    show = showEditMaterialDialog,
                    materialName = materialToEdit.materialName,
                    quantity = if (materialQuantityToEdit.isNotBlank()) materialQuantityToEdit.toInt() else 0,
                    onDismiss = { showEditMaterialDialog = false },
                    onQuantityValueChange = { materialQuantityToEdit = it },
                    onAccept = {
                        viewModel.updateMaterialInProduct(
                            MaterialInProductWithQuantityDataClass(
                                material = materialToEdit,
                                quantity = materialQuantityToEdit.toInt(),
                            )
                        )
                    },
                    onDelete = {
                        viewModel.deleteMaterialInProduct(materialToEdit.materialName)
                    }
                )
            }
        }
    }

}

@Composable
private fun EditMaterialInProductDialog(
    show: Boolean,
    materialName: String,
    quantity: Int,
    onDismiss: () -> Unit,
    onQuantityValueChange: (String) -> Unit,
    onAccept: () -> Unit,
    onDelete: () -> Unit
) {

    if (!show) return

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Purple40
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    SecondTitleItem("Editar material")
                    HorizontalDivider(thickness = 2.dp, color = Color.White)
                }
            }
            SingleLineTextFieldItem(
                value = materialName,
                label = "",
                enabled = false
            ) { }
            NumericTextField(
                value = quantity.toString(),
                label = "Cantidad"
            ) { onQuantityValueChange(it) }
            AcceptDeclineButtons(
                acceptText = "Agregar",
                declineText = "Cancelar",
                onAccept = {
                    onAccept()
                    onDismiss()
                },
                onDecline = { onDismiss() },
                enabled = (quantity != 0 && quantity.toString().isNotBlank())
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                SimpleButtonText("Eliminar Material", Color.Red) {
                    showConfirmDialog = true
                }
            }
            ConfirmDialog(
                show = showConfirmDialog,
                text = "¿Estás segura que queres eliminar el material?",
                onAccept = {
                    onDelete()
                    showConfirmDialog = false
                    onDismiss()
                },
                onDismiss = { showConfirmDialog = false }
            )

        }
    }
}

@Composable
private fun MaterialsInProductRowItem(
    material: MaterialInProductWithQuantityDataClass,
    onClick: (MaterialInProductWithQuantityDataClass) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick(material)
            }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(material.material.materialName, modifier = Modifier.weight(.8f))
        Text(material.quantity.toString(), modifier = Modifier.weight(.5f))
        Text(
            getMaterialCost(
                material.quantity,
                getUnitPrice(material.material.pricePerPack, material.material.quantityPerPack)
            ).toString(), modifier = Modifier.weight(.2f)
        )
    }
}

fun getMaterialCost(quantity: Int, unitPrice: Int): Int = quantity * unitPrice
fun getUnitPrice(pricePerPack: Int, quantityPerPack: Int): Int = ceil(pricePerPack.toDouble() / quantityPerPack).toInt()


