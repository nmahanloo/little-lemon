// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(navController: NavHostController) {
    var searchPhrase by remember { mutableStateOf("") }
    var dishes by remember { mutableStateOf<List<Dish>>(emptyList()) }

    // Load menu on first composition
    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            DishRepository.fetchMenuFromNetwork()
            dishes = DishRepository.dishes
        }
    }

    // Filter based on user input
    val filteredDishes = dishes.filter {
        it.name.contains(searchPhrase, ignoreCase = true)
    }

    Column(modifier = Modifier) {
        TopAppBar()
        UpperPanel(onSearchChanged = { searchPhrase = it })
        LowerPanel(navController, filteredDishes)
    }
}

