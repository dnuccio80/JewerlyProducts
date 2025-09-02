package com.example.jewerlyproducts.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.NumericTextField
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.components.TextFieldWithDropdownMenu
import com.example.jewerlyproducts.ui.theme.Purple40
import com.example.jewerlyproducts.ui.theme.Purple80

@Composable
fun HomeScreen(innerPadding: PaddingValues, viewModel: HomeViewModel = hiltViewModel()) {

    val showSellDialog by viewModel.showAddSellDialog.collectAsState()
    val showExpensesDialog by viewModel.showAddExpensesDialog.collectAsState()
    val quantitySell by viewModel.quantitySell.collectAsState()
    val expensesDescription by viewModel.expensesDescription.collectAsState()
    val expensesPrice by viewModel.expensesPrice.collectAsState()

    ScreenBackgroundComponent(innerPadding) {
        FirstTitleItem("Inicio")
        BalanceCardItem()
        SellsCardItem()
        ExpensesCardItem()
    }
    Box(Modifier.fillMaxSize()) {
        HomeFabItem(
            Modifier.align(Alignment.BottomEnd),
            innerPadding,
            onNewSell = { viewModel.updateShowSellDialog(true) },
            onNewExpense = { viewModel.updateShowAddExpensesDialog(true) }
        )
    }
    // Sell Dialog
    SellDialog(
        showSellDialog,
        "Agregar Venta",
        quantitySell,
        onQuantitySellChange = { viewModel.updateQuantitySell(it) },
        onDismiss = {
            viewModel.updateShowSellDialog(false)
            viewModel.clearSellStats()
        }
    )
    {
        viewModel.updateShowSellDialog(
            false
        )
    }
    ExpensesDialog(
        showExpensesDialog,
        expensesDescription,
        price = expensesPrice,
        onDescriptionChange = { viewModel.updateExpensesDescription(it) },
        onPriceChange = { viewModel.updateExpensesPrice(it) },
        onAddExpenses = {  }
    ) {
        viewModel.updateShowAddExpensesDialog(
            false
        )
        viewModel.clearExpensesStats()
    }

}

@Composable
fun ExpensesDialog(
    show: Boolean,
    description: String,
    price: String,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onAddExpenses: () -> Unit,
    onDismiss: () -> Unit
) {

    if (!show) return

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Purple40
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    SecondTitleItem("Agregar Gasto")
                    HorizontalDivider(thickness = 2.dp, color = Color.White)
                }
                SingleLineTextFieldItem(
                    value = description,
                    label = "Descripción"
                ) {
                    onDescriptionChange(it)
                }
                NumericTextField(
                    value = price,
                    label = "Costo"
                ) { onPriceChange(it) }
                AcceptDeclineButtons(
                    acceptText = "Agregar",
                    declineText = "Cancelar",
                    onAccept = {
                        onAddExpenses()
                        onDismiss()
                    },
                    onDecline = { onDismiss() },
                    enabled = (description.isNotBlank() && price.isNotBlank())
                )
            }
        }
    }
}


@Composable
fun SellDialog(
    show: Boolean,
    title: String,
    quantitySell: String,
    onQuantitySellChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    if (!show) return

    var articleName by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Purple40
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    SecondTitleItem(title)
                    HorizontalDivider(thickness = 2.dp, color = Color.White)
                }
                TextFieldWithDropdownMenu("Articulo", articleName) { articleName = it }
                NumericTextField(
                    value = quantitySell,
                    label = "Cantidad"
                ) { onQuantitySellChange(it) }
                AcceptDeclineButtons(
                    acceptText = "Agregar",
                    declineText = "Cancelar",
                    onAccept = {
                        onAccept()
                        onDismiss()
                    },
                    onDecline = { onDismiss() },
                    enabled = (articleName.isNotBlank() && quantitySell.isNotBlank())
                )
            }
        }
    }
}




@Composable
fun HomeFabItem(
    modifier: Modifier,
    innerPadding: PaddingValues,
    onNewSell: () -> Unit,
    onNewExpense: () -> Unit
) {

    var expandedValue by rememberSaveable { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { expandedValue = true },
        modifier = modifier.padding(
            bottom = innerPadding.calculateBottomPadding() + 16.dp,
            end = 8.dp
        ),
        containerColor = Color.Magenta,
        contentColor = Color.White

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add button")
        DropdownMenuHomeItem(
            expandedValue,
            onDismiss = { expandedValue = false },
            onNewSell = {
                onNewSell()
                expandedValue = false
            },
            onNewExpense = {
                onNewExpense()
                expandedValue = false
            }
        )
    }
}

@Composable
fun DropdownMenuHomeItem(
    expandedValue: Boolean,
    onDismiss: () -> Unit,
    onNewExpense: () -> Unit,
    onNewSell: () -> Unit
) {
    DropdownMenu(
        expanded = expandedValue,
        onDismissRequest = { onDismiss() },
        offset = DpOffset((-34).dp, 0.dp),
        modifier = Modifier.background(Color.Magenta)
    ) {
        DropdownMenuItem(
            text = { Text("Agregar venta") },
            onClick = { onNewSell() }
        )
        DropdownMenuItem(
            text = { Text("Agregar gasto") },
            onClick = { onNewExpense() }
        )
    }
}

@Composable
private fun ExpensesCardItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Purple40
        )
    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            FirstTitleItem("Gastos")
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.White
            )
            Spacer(Modifier.size(4.dp))
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                ), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
            TwoRowItem("Gastos varios", 50000)
        }
    }
}

@Composable
private fun SellsCardItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Purple40
        )
    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            FirstTitleItem("Ventas")
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.White
            )
            Spacer(Modifier.size(4.dp))
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                ), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ThreeRowItem("Pulsera Roma", 100, 12500)
            ThreeRowItem("Pulsera Roma", 100, 12500)
            ThreeRowItem("Pulsera Roma", 100, 12500)
            ThreeRowItem("Pulsera Roma", 100, 12500)
            ThreeRowItem("Pulsera Roma", 100, 12500)
        }
    }
}

@Composable
fun BalanceCardItem() {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Purple40
            )
        ) {


            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(Modifier.fillMaxWidth()) {
                    FirstTitleItem("Balance")
                    HorizontalDivider(
                        Modifier.fillMaxWidth(),
                        thickness = 2.dp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                }
                FirstTitleItem("$ 220.000", Color.Green)
                BodyText("Ventas hechas: $102.000")
                BodyText("Gastos: $52.000")
            }
        }
    }
}


@Composable
private fun ThreeRowItem(title: String, quantity: Int, price: Int) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            modifier = Modifier.weight(.9f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        BodyText("x$quantity", modifier = Modifier.weight(.3f))
        BodyText("$$price", modifier = Modifier.weight(.4f))
    }
}

@Composable
private fun TwoRowItem(description: String, value: Int) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            description,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        BodyText("$$value")
    }
}

