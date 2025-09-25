package com.example.jewerlyproducts.ui.screens.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.jewerlyproducts.ui.components.SingleLineTextFieldItem
import com.example.jewerlyproducts.ui.components.SmallButtonText
import com.example.jewerlyproducts.ui.components.TextFieldWithDropdownMenu
import com.example.jewerlyproducts.ui.dataclasses.ExpenseDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductSaver
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import com.example.jewerlyproducts.ui.dataclasses.SellDataClass
import com.example.jewerlyproducts.ui.theme.AccentColor
import com.example.jewerlyproducts.ui.theme.BackAccentColor
import com.example.jewerlyproducts.ui.theme.LightBrown
import com.example.jewerlyproducts.ui.theme.LightBrownCard
import com.example.jewerlyproducts.ui.theme.MainColor
import com.example.jewerlyproducts.ui.theme.MainGreen
import com.example.jewerlyproducts.ui.theme.MainLight
import com.example.jewerlyproducts.ui.theme.Purple40
import com.example.jewerlyproducts.ui.theme.RudeRed
import com.example.jewerlyproducts.ui.theme.SecondLight
import com.example.jewerlyproducts.ui.theme.SecondLightCard
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(innerPadding: PaddingValues, viewModel: HomeViewModel = hiltViewModel()) {

    val showSellDialog by viewModel.showAddSellDialog.collectAsState()
    val showExpensesDialog by viewModel.showAddExpensesDialog.collectAsState()

    var showClearBalanceDialog by rememberSaveable { mutableStateOf(false) }
    var showClearSellsDialog by rememberSaveable { mutableStateOf(false) }
    var showClearExpensesDialog by rememberSaveable { mutableStateOf(false) }

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
        FirstTitleItem("Inicio", color = SecondLight)
        BalanceCardItem(totalSells, totalExpenses) {
            showClearBalanceDialog = true
        }
        SellsCardItem(
            sellList,
            onClearSell = { showClearSellsDialog = true },
            onEditSell = { sell ->
                viewModel.updateSell(sell)
            },
            onDelete = { sellId -> viewModel.deleteSell(sellId) }
        )
        ExpensesCardItem(
            expensesList,
            onClearExpenses = { showClearExpensesDialog = true },
            onEditExpense = { expense ->
                viewModel.updateExpense(expense)
                showClearExpensesDialog = false
            },
            onDelete = { expenseId ->
                viewModel.deleteExpense(expenseId)

            }
        )
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
    ConfirmDialog(
        show = showClearSellsDialog,
        text = "¿Segura que deseas eliminar toda la información de ventas?",
        onAccept = {
            viewModel.clearAllSells()
            showClearSellsDialog = false
        }
    ) { showClearSellsDialog = false }
    ConfirmDialog(
        show = showClearExpensesDialog,
        text = "¿Segura que deseas eliminar toda la información de gastos?",
        onAccept = {
            viewModel.clearAllExpenses()
            showClearExpensesDialog = false
        }
    ) { showClearExpensesDialog = false }

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
                containerColor = AccentColor
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, MainColor)
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
fun ExpensesDialogEdit(
    show: Boolean,
    expenseId: Int,
    description: String,
    price: String,
    onModifyExpense: (ExpenseDataClass) -> Unit,
    onModifyDescription: (String) -> Unit,
    onModifyPrice: (String) -> Unit,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {

    if (!show) return

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = AccentColor
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, MainLight)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    SecondTitleItem("Modificar Gasto")
                    HorizontalDivider(thickness = 2.dp, color = Color.White)
                }
                SingleLineTextFieldItem(
                    value = description,
                    label = "Descripción"
                ) {
                    onModifyDescription(it)
                }
                NumericTextField(
                    value = price,
                    label = "Costo"
                ) { onModifyPrice(it) }
                AcceptDeclineButtons(
                    acceptText = "Modificar",
                    declineText = "Borrar",
                    declineColor = RudeRed,
                    onAccept = {
                        onModifyExpense(
                            ExpenseDataClass(
                                expenseId = expenseId,
                                description = description,
                                value = price.toInt()
                            )
                        )
                        onDismiss()
                    },
                    onDecline = { onDelete() },
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
        containerColor = AccentColor,
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
        modifier = Modifier.background(AccentColor)
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
private fun ExpensesCardItem(
    expensesList: List<ExpenseDataClass>,
    onClearExpenses: () -> Unit,
    onEditExpense: (ExpenseDataClass) -> Unit,
    onDelete: (Int) -> Unit
) {

    var showEditExpenseDialog by rememberSaveable { mutableStateOf(false) }
    var showConfirmDeleteExpenseDialog by rememberSaveable { mutableStateOf(false) }
    var expenseId by rememberSaveable { mutableIntStateOf(0) }
    var description by rememberSaveable { mutableStateOf("") }
    var value by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = SecondLightCard
        ),
        border = BorderStroke(0.3.dp, MainLight)

    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FirstTitleItem("Gastos", color = MainLight)

                SmallButtonText(
                    text = "Limpiar",
                    color = AccentColor,
                    onClick = { onClearExpenses() }
                )
            }
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
                    TwoRowItem(expense.description, formatToPrice(expense.value)) {
                        expenseId = expense.expenseId
                        description = expense.description
                        value = expense.value.toString()
                        showEditExpenseDialog = true

                    }
                }
            } else {
                BodyText("No hay gastos agendados")
            }
        }
        ExpensesDialogEdit(
            show = showEditExpenseDialog,
            expenseId = expenseId,
            description = description,
            price = value,
            onModifyExpense = { expense ->
                onEditExpense(expense)
                showEditExpenseDialog = false
            },
            onDismiss = {
                showEditExpenseDialog = false
            },
            onDelete = {
                showConfirmDeleteExpenseDialog = true
            },
            onModifyDescription = { description = it },
            onModifyPrice = { value = it }
        )
        ConfirmDialog(
            show = showConfirmDeleteExpenseDialog,
            text = "¿Segura que querés eliminar el gasto?",
            onAccept = {
                onDelete(expenseId)
                showConfirmDeleteExpenseDialog = false
                showEditExpenseDialog = false
            },
            onDismiss = {
                showConfirmDeleteExpenseDialog = false
            }
        )
    }
}

@Composable
private fun SellsCardItem(
    sellList: List<SellDataClass>,
    onClearSell: () -> Unit,
    onEditSell: (SellDataClass) -> Unit,
    onDelete: (Int) -> Unit
) {

    var showEditSell by rememberSaveable { mutableStateOf(false) }
    var productSelected by rememberSaveable(saver = ProductSaver) {
        mutableStateOf(
            ProductsDataClass(
                productName = "",
                sellValue = 0,
                imageUri = ""
            )
        )
    }
    var quantity by rememberSaveable { mutableStateOf("") }
    var showConfirmDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var sellId by rememberSaveable { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightBrownCard
        ),
        border = BorderStroke(0.3.dp, MainLight)


    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FirstTitleItem("Ventas", color = Color.White)
                SmallButtonText(
                    text = "Limpiar",
                    color = AccentColor,
                    onClick = { onClearSell() }
                )
            }
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
                    ) {
                        productSelected = sell.product
                        quantity = sell.quantity.toString()
                        sellId = sell.sellId
                        showEditSell = true
                    }
                }
            }
        }
        DialogWithListAndQuantitySellEdit(
            show = showEditSell,
            item = productSelected,
            quantitySell = quantity,
            onQuantitySellChange = { quantity = it },
            onDismiss = { showEditSell = false },
            onAccept = { product, quantity ->
                onEditSell(
                    SellDataClass(
                        sellId = sellId,
                        product = product,
                        quantity = quantity
                    )
                )
                showEditSell = false
            }
        ) { showConfirmDeleteDialog = true }
        ConfirmDialog(
            show = showConfirmDeleteDialog,
            text = "¿Segura que querés eliminar la venta?",
            onAccept = {
                onDelete(sellId)
                showConfirmDeleteDialog = false
                showEditSell = false
            },
            onDismiss = {
                showConfirmDeleteDialog = false
            }
        )
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
                containerColor = MainColor
            ),
            border = BorderStroke(0.3.dp, MainLight)

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
                        FirstTitleItem("Balance", color = MainLight)
                        SmallButtonText(
                            text = "Limpiar",
                            color = AccentColor,
                            onClick = { onClearBalance() }
                        )
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
private fun ThreeRowItem(title: String, quantity: Int, price: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
private fun TwoRowItem(description: String, value: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                containerColor = AccentColor
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            border = BorderStroke(1.dp, MainLight)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    SecondTitleItem("Agregar venta")
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
                    acceptColor = MainGreen,
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

@Composable
private fun DialogWithListAndQuantitySellEdit(
    show: Boolean,
    item: ProductsDataClass,
    quantitySell: String,
    onQuantitySellChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onAccept: (ProductsDataClass, Int) -> Unit,
    onDelete: () -> Unit
) {
    if (!show) return

    val articleName by rememberSaveable { mutableStateOf(item.productName) }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = AccentColor
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            border = BorderStroke(1.dp, MainLight)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    SecondTitleItem("Modificar Venta")
                    HorizontalDivider(thickness = 2.dp, color = Color.White)
                }
                TextField(
                    value = articleName,
                    onValueChange = { },
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledTextColor = Color.White
                    ),
                    trailingIcon = {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                )
                NumericTextField(
                    value = quantitySell,
                    label = "Cantidad"
                ) { onQuantitySellChange(it) }
                AcceptDeclineButtons(
                    acceptText = "Modificar",
                    declineText = "Borrar",
                    declineColor = RudeRed,
                    acceptColor = MainGreen,
                    onAccept = {
                        onAccept(item, quantitySell.toInt())
                        onDismiss()
                    },
                    onDecline = {
                        onDelete()
                    },
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
        MainLight
    } else if (balance < 0) {
        RudeRed
    } else MainLight
}