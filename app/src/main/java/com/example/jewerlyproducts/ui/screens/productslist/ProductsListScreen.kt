package com.example.jewerlyproducts.ui.screens.productslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.FloatingButton
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.theme.Purple40

@Composable
fun ProductsListScreen(
    innerPadding: PaddingValues,
    onNavigateToNewProduct:() -> Unit,
    productsListViewModel: ProductsListViewModel = hiltViewModel(),
) {

    val verticalScroll = rememberScrollState()

    ScreenBackgroundComponent(innerPadding)
    {
        FirstTitleItem("Listado de productos")
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScroll),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
            ProductItem()
        }

    }
    FloatingButton(innerPadding) { onNavigateToNewProduct() }
}


@Composable
fun ProductItem() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navigate to edit product details screen
            },
        colors = CardDefaults.cardColors(
            containerColor = Purple40.copy(alpha = 1f)
        ),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Red)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(1f)
            ) {
                SecondTitleItem("Este es el titulo".uppercase())
                BodyText("Costo: $1200")
                BodyText("Valor de venta: $5000")
            }
        }
    }
}