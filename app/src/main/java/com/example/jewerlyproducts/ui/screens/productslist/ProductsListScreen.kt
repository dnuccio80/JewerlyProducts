package com.example.jewerlyproducts.ui.screens.productslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jewerlyproducts.ui.components.BodyText
import com.example.jewerlyproducts.ui.components.FirstTitleItem
import com.example.jewerlyproducts.ui.components.FloatingButton
import com.example.jewerlyproducts.ui.components.ScreenBackgroundComponent
import com.example.jewerlyproducts.ui.components.SecondTitleItem
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import com.example.jewerlyproducts.ui.theme.Purple40
import androidx.core.net.toUri
import com.example.jewerlyproducts.R

@Composable
fun ProductsListScreen(
    innerPadding: PaddingValues,
    onNavigateToNewProduct: () -> Unit,
    viewModel: ProductsListViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit
) {

    val productsList by viewModel.productList.collectAsState()

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
            if (productsList.isEmpty()) {
                SecondTitleItem("No hay nada para mostrar")
            } else {
                productsList.forEach { product ->
                    ProductItem(product) { onNavigateToDetails(product.productName) }
                }
            }
        }
    }
    FloatingButton(innerPadding) { onNavigateToNewProduct() }
}


@Composable
fun ProductItem(product: ProductsDataClass, onNavigateToDetails: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToDetails()
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
            AsyncImage(
                model = product.imageUri.toUri(),
                contentDescription = "",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.image_loading),
                error = painterResource(R.drawable.image_error)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(1f)
            ) {
                SecondTitleItem(product.productName)
                BodyText("Valor de venta: ${product.sellValue}")
            }
        }
    }
}