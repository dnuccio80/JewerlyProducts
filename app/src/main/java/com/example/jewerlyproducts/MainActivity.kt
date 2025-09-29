package com.example.jewerlyproducts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jewerlyproducts.ui.routes.Routes
import com.example.jewerlyproducts.ui.screens.home.HomeScreen
import com.example.jewerlyproducts.ui.screens.materialdetails.MaterialDetailsScreen
import com.example.jewerlyproducts.ui.screens.materialslist.MaterialsListScreen
import com.example.jewerlyproducts.ui.screens.newmaterial.AddNewMaterialScreen
import com.example.jewerlyproducts.ui.screens.newproduct.AddNewProductScreen
import com.example.jewerlyproducts.ui.screens.productdetails.ProductDetailsScreen
import com.example.jewerlyproducts.ui.screens.productslist.ProductsListScreen
import com.example.jewerlyproducts.ui.theme.JewerlyProductsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            JewerlyProductsTheme {

                val mainNav = rememberNavController()

                val navBackStackEntry by mainNav.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                var currentTarget by rememberSaveable { mutableIntStateOf(BottomBarNavAction.HOME.ordinal) }

                LaunchedEffect(currentRoute) {
                    currentTarget = when (currentRoute) {
                        Routes.Home.routes -> BottomBarNavAction.HOME.ordinal
                        Routes.ProductList.routes -> BottomBarNavAction.PRODUCTS.ordinal
                        Routes.MaterialList.routes -> BottomBarNavAction.MATERIALS.ordinal
                        else -> BottomBarNavAction.HOME.ordinal
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(currentTarget) { action ->
                            when (action) {
                                BottomBarNavAction.HOME -> {
                                    if (currentRoute != Routes.Home.routes) {
                                        mainNav.navigate(Routes.Home.routes) {
                                            popUpTo(mainNav.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }

                                BottomBarNavAction.PRODUCTS -> {
                                    if (currentRoute != Routes.ProductList.routes) {
                                        mainNav.navigate(Routes.ProductList.routes) {
                                            popUpTo(mainNav.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true

                                        }
                                    }
                                }

                                BottomBarNavAction.MATERIALS -> {
                                    if (currentRoute != Routes.MaterialList.routes) {
                                        mainNav.navigate(Routes.MaterialList.routes) {
                                            popUpTo(mainNav.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = mainNav,
                        startDestination = Routes.Home.routes
                    ) {
                        composable(Routes.Home.routes) { HomeScreen(innerPadding) }
                        composable(Routes.ProductList.routes) {
                            ProductsListScreen(
                                innerPadding,
                                onNavigateToNewProduct = { mainNav.navigate(Routes.AddNewProduct.routes) },
                                onNavigateToDetails = { productName ->
                                    mainNav.navigate(
                                        Routes.ProductDetails.createRoute(productName)
                                    )
                                }

                            )
                        }
                        composable(Routes.AddNewProduct.routes) {
                            AddNewProductScreen(
                                innerPadding,
                                onAccept = {},
                                onDismiss = { mainNav.popBackStack() },
                            )
                        }
                        composable(Routes.MaterialList.routes) {
                            MaterialsListScreen(
                                innerPadding,
                                onNavigateToAddNewMaterial = { mainNav.navigate(Routes.AddNewMaterial.routes) },
                                onNavigateToMaterialDetails = { materialName ->
                                    mainNav.navigate(
                                        Routes.MaterialDetail.createRoute(materialName)
                                    )
                                }

                            )
                        }
                        composable(Routes.AddNewMaterial.routes) {
                            AddNewMaterialScreen(
                                innerPadding,
                                onAddMaterial = {},
                                onDismiss = { mainNav.popBackStack() }
                            )
                        }
                        composable(
                            Routes.MaterialDetail.routes,
                            arguments = listOf(navArgument("materialName") {
                                type = NavType.StringType
                            })
                        ) { navBackStackEntry ->
                            MaterialDetailsScreen(
                                innerPadding = innerPadding,
                                materialName = navBackStackEntry.arguments?.getString("materialName")
                                    .orEmpty(),
                                onDismiss = { mainNav.popBackStack() }
                            )
                        }
                        composable(
                            Routes.ProductDetails.routes,
                            arguments = listOf(navArgument("productName") {
                                type = NavType.StringType
                            })
                        ) { navBackStackEntry ->
                            ProductDetailsScreen(
                                innerPadding = innerPadding,
                                productName = navBackStackEntry.arguments?.getString("productName")
                                    .orEmpty(),
                            ) { mainNav.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BottomBar(currentTarget: Int, onClick: (BottomBarNavAction) -> Unit) {
    NavigationBar(
        Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        NavigationBarItem(
            selected = currentTarget == BottomBarNavAction.HOME.ordinal,
            onClick = { onClick(BottomBarNavAction.HOME) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "", tint = Color.White) },
            label = { Text("Inicio", color = Color.White) }
        )
        NavigationBarItem(
            selected = currentTarget == BottomBarNavAction.PRODUCTS.ordinal,
            onClick = { onClick(BottomBarNavAction.PRODUCTS) },
            icon = {
                Icon(
                    painterResource(R.drawable.ic_shopping_bag),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            label = { Text("Productos", color = Color.White) }
        )
        NavigationBarItem(
            selected = currentTarget == BottomBarNavAction.MATERIALS.ordinal,
            onClick = { onClick(BottomBarNavAction.MATERIALS) },
            icon = {
                Icon(
                    painterResource(R.drawable.ic_cart),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            label = { Text("Materiales", color = Color.White) }
        )
    }
}

enum class BottomBarNavAction {
    HOME, PRODUCTS, MATERIALS
}