package com.example.jewerlyproducts.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.AcceptDeclineButtons
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.ConfirmDialog
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.NumericTextField
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.components.SimpleButtonText
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.components.TextFieldWithDropdownMenu
import com.example.jewerlyproducts.ui.dataclasses.ExpenseDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductSaver
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import com.example.jewerlyproducts.ui.dataclasses.SellDataClass
import com.example.jewerlyproducts.ui.theme.Pink40
import com.example.jewerlyproducts.ui.theme.Purple40
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(innerPadding: PaddingValues, viewModel: HomeViewModel = hiltViewModel()) {

    val showSellDialog by viewModel.showAddSellDialog.collectAsState()
    val showExpensesDialog by viewModel.showAddExpensesDialog.collectAsState()
    var showClearBalanceDialog by rememberSaveable { mutableStateOf(false) }

    val quantitySell by viewModel.quantitySell.collectAsState()
    val expensesDescription by viewModel.expensesDescription.collectAsState()
    val expensesPrice by viewModel.expensesPrice.collectAsState()

    val productList by viewModel.productList.collectAsState()
    val sellList by viewModel.sellList.collectAsState()
    val expensesList by viewModel.expensesList.collectAsState()
    val totalSells by viewModel.totalSells.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()

    LaunchedEffect(sellList) {
        viewModel.updateTotalSells()
    }

    LaunchedEffect(expensesList) {
        viewModel.updateTotalExpenses()
    }

    ScreenBackgroundComponent(innerPadding) {
        FirstTitleItem("Inicio")
        BalanceCardItem(totalSells, totalExpenses) {
            showClearBalanceDialog = true
        }
        SellsCardItem(sellList)
        ExpensesCardItem(expensesList)
    }
    Box(Modifier.fillMaxSize()) {
        HomeFabItem(
            Modifier.align(Alignment.BottomEnd),
            innerPadding,
            onNewSell = { viewModel.updateShowSellDialog(true) },
            onNewExpense = { viewModel.updateShowAddExpensesDialog(true) }
        )
    }
    DialogWithListAndQuantitySell(
        show = showSellDialog,
        title = "Agregar Venta",
        itemList = productList,
        quantitySell = quantitySell,
        onQuantitySellChange = {
            viewModel.updateQuantitySell(it)
        },
        onDismiss = {
            viewModel.updateShowSellDialog(false)
            viewModel.clearSellStats()
        },
        onAccept = { product, quantity ->
            viewModel.updateShowSellDialog(false)
            viewModel.addSell(
                product = product,
                quantity = quantity
            )
        }
    )

    ExpensesDialog(
        showExpensesDialog,
        expensesDescription,
        price = expensesPrice,
        onDescriptionChange = { viewModel.updateExpensesDescription(it) },
        onPriceChange = { viewModel.updateExpensesPrice(it) },
        onAddExpenses = {
            viewModel.addExpense(expensesDescription, expensesPrice.toInt())
            viewModel.updateShowAddExpensesDialog(
                false
            )
            viewModel.clearExpensesStats()
        }
    ) {
        viewModel.updateShowAddExpensesDialog(
            false
        )
        viewModel.clearExpensesStats()
    }
    ConfirmDialog(
        show = showClearBalanceDialog,
        text = "¿Segura que deseas eliminar toda la información de ventas y gastos?",
        onAccept = {
            viewModel.clearBalance()
            showClearBalanceDialog = false
        }
    ) { showClearBalanceDialog = false }

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
private fun ExpensesCardItem(expensesList: List<ExpenseDataClass>) {
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
            if (expensesList.isNotEmpty()) {
                expensesList.forEach { expense ->
                    TwoRowItem(expense.description, formatToPrice(expense.value))

                }
            } else {
                BodyText("No hay gastos agendados")
            }
        }
    }
}

@Composable
private fun SellsCardItem(sellList: List<SellDataClass>) {
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
            if (sellList.isEmpty()) {
                BodyText("No hay ventas registradas")
            } else {
                sellList.forEach { sell ->
                    ThreeRowItem(
                        sell.product.productName, sell.quantity, formatToPrice(
                            getSellValue(
                                sellValue = sell.product.sellValue,
                                quantity = sell.quantity
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceCardItem(totalSells: Int, totalExpenses: Int, onClearBalance: () -> Unit) {

    val balance = getBalance(totalSells, totalExpenses)

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
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FirstTitleItem("Balance")
                        SimpleButtonText(
                            text = "Limpiar",
                            color = Pink40
                        ) {
                            onClearBalance()
                        }
                    }
                    HorizontalDivider(
                        Modifier.fillMaxWidth(),
                        thickness = 2.dp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                }
                FirstTitleItem(formatToPrice(balance), getBalanceColor(balance))
                BodyText("Ventas hechas: ${formatToPrice(totalSells)}")
                BodyText("Gastos: ${formatToPrice(totalExpenses)}")
            }
        }
    }
}


@Composable
private fun ThreeRowItem(title: String, quantity: Int, price: String) {
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
        BodyText(price, modifier = Modifier.weight(.4f))
    }
}

@Composable
private fun TwoRowItem(description: String, value: String) {
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
        BodyText(value)
    }
}

private fun getSellValue(sellValue: Int, quantity: Int): Int = sellValue * quantity

@Composable
private fun DialogWithListAndQuantitySell(
    show: Boolean,
    title: String,
    itemList: List<ProductsDataClass>,
    quantitySell: String,
    onQuantitySellChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onAccept: (ProductsDataClass, Int) -> Unit
) {
    if (!show) return

    var articleName by rememberSaveable { mutableStateOf("") }
    val productSelected = rememberSaveable(saver = ProductSaver) {
        mutableStateOf(
            ProductsDataClass(
                productName = "",
                sellValue = 0,
                imageUri = ""
            )
        )
    }

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
                    SecondTitleItem(title)
                    HorizontalDivider(thickness = 2.dp, color = Color.White)
                }
                TextFieldWithDropdownMenu(itemList, "Seleccionar..", articleName) { item ->
                    when (item) {
                        is ProductsDataClass -> {
                            articleName = item.productName
                            productSelected.value = item
                        }
                    }
                }
                NumericTextField(
                    value = quantitySell,
                    label = "Cantidad"
                ) { onQuantitySellChange(it) }
                AcceptDeclineButtons(
                    acceptText = "Agregar",
                    declineText = "Cancelar",
                    onAccept = {
                        onAccept(productSelected.value, quantitySell.toInt())
                        onDismiss()
                    },
                    onDecline = { onDismiss() },
                    enabled = (articleName.isNotBlank() && quantitySell.isNotBlank())
                )
            }
        }
    }
}

private fun getBalance(sells: Int, expenses: Int): Int = sells - expenses

fun formatToPrice(number: Int): String {
    val localeAR = Locale("es", "AR")
    val formatter = NumberFormat.getInstance(localeAR)
    return "$${formatter.format(number)}"
}

fun getBalanceColor(balance: Int): Color {
    return if (balance > 0) {
        Color.Green
    } else if (balance < 0) {
        Color.Red
    } else Color.DarkGray
}