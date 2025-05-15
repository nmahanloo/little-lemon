// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun LowerPanel(navController: NavHostController, dishes: List<Dish> = listOf()) {
    var selectedCategory by remember { mutableStateOf("") }

    val filteredDishes = if (selectedCategory.isBlank()) dishes else dishes.filter {
        it.category.equals(selectedCategory, ignoreCase = true)
    }

    Column {
        WeeklySpecialCard(
            onCategorySelected = { category -> selectedCategory = category }
        )
        LazyColumn {
            itemsIndexed(filteredDishes) { _, dish ->
                MenuDish(navController, dish)
            }
        }
    }
}

@Composable
fun WeeklySpecialCard(onCategorySelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.order_for_delivery),
            style = MaterialTheme.typography.h1.copy(
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("starters", "mains", "desserts", "drinks").forEach { category ->
                Button(
                    onClick = { onCategorySelected(category) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        contentColor = colorResource(id = R.color.green)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = category.replaceFirstChar { it.uppercaseChar() },
                        fontSize = 16.sp
                    )
                }
            }

            TextButton(
                onClick = { onCategorySelected("") },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorResource(id = R.color.green),
                    backgroundColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("X", fontSize = 20.sp)
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 2.dp),
            thickness = 2.dp,
            color = colorResource(id = R.color.medium_gray)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuDish(navController: NavHostController? = null, dish: Dish) {
    Card(onClick = {
        Log.d("AAA", "Click ${dish.id}")
        navController?.navigate(DishDetails.route + "/${dish.id}")
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 24.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                )
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(bottom = 5.dp)
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 16.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                )
            }
            AsyncImage(
                model = dish.imageUrl,
                contentDescription = "Dish Image",
                modifier = Modifier
                    .size(160.dp)
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
    )
}
