package com.example.jewerlyproducts.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jewerlyproducts.ui.dataclasses.MaterialsDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import com.example.jewerlyproducts.ui.theme.Purple40
import com.example.jewerlyproducts.ui.theme.Purple80

@Composable
fun FirstTitleItem(text: String, color: Color = Color.White) {
    Text(text, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = color)
}

@Composable
fun SecondTitleItem(text: String) {
    Text(text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start) {
    Text(text, modifier = modifier, fontSize = 14.sp, color = Color.White, textAlign = textAlign)
}

@Composable
fun ScreenBackgroundComponent(innerPadding: PaddingValues, content: @Composable () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun FloatingButton(innerPadding: PaddingValues, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = innerPadding.calculateBottomPadding() + 32.dp, end = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            containerColor = Color.Magenta,
            contentColor = Color.White
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add button", tint = Color.White)
        }
    }
}

@Composable
fun AcceptDeclineButtons(
    acceptText: String,
    declineText: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { onDecline() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray.copy(alpha = .5f)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            BodyText(declineText)
        }
        Button(
            onClick = { onAccept() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green.copy(alpha = .5f)
            ),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp)
        ) {
            BodyText(acceptText)
        }
    }
}

@Composable
fun ConfirmDialog(show: Boolean, text: String, onAccept: () -> Unit, onDismiss: () -> Unit) {

    if (!show) return

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp), colors = CardDefaults.cardColors(
                containerColor = Purple40
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyText(text, textAlign = TextAlign.Center)
                AcceptDeclineButtons(
                    acceptText = "Confirmar",
                    declineText = "Cancelar",
                    onAccept = { onAccept() },
                    onDecline = { onDismiss() },
                )
            }
        }
    }
}

@Composable
fun SimpleButtonText(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        BodyText(text)
    }
}

@Composable
fun SingleLineTextFieldItem(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { BodyText(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.DarkGray.copy(alpha = .2f)
        ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        singleLine = true,
    )
}

@Composable
fun NumericTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { BodyText(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.DarkGray.copy(alpha = .2f)
        ),
        singleLine = true,
    )
}

@Composable
fun TextFieldAreaItem(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.DarkGray.copy(alpha = .2f)
        ),
        maxLines = 4,
        minLines = 4,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextFieldWithDropdownMenu(itemList:List<Any>, placeholder: String, value: String, onValueChange: (String) -> Unit) {

    var showMenu by rememberSaveable { mutableStateOf(false) }

    Column {
        TextField(
            value = value,
            onValueChange = { },
            placeholder = { BodyText(placeholder) },
            modifier = Modifier.clickable {
                showMenu = true
            },
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
        DropdownMenu(
            showMenu,
            scrollState = rememberScrollState(),
            onDismissRequest = { showMenu = false },
            containerColor = Purple80,
            modifier = Modifier.heightIn(min = 60.dp, max = 150.dp)
        ) {
            itemList.forEach {
                when(it) {
                    is MaterialsDataClass -> {
                        DropdownMenuItem(
                            text = { BodyText(it.name) },
                            onClick = {
                                onValueChange(it.name)
                                showMenu = false
                            }
                        )
                    }
                    is ProductsDataClass -> {

                    }
                }
            }
        }
    }

}

@Composable
fun DialogWithListAndQuantity(
    show: Boolean,
    title: String,
    itemList:List<Any>,
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
                TextFieldWithDropdownMenu(itemList, "Seleccionar..", articleName) { articleName = it }
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


