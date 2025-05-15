// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun DishDetails(id: Int) {
    val dish = requireNotNull(DishRepository.getDish(id))
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        val context = LocalContext.current
        TopAppBar(onBackClick = { (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed() })

        AsyncImage(
            model = dish.imageUrl,
            contentDescription = "Dish image",
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = dish.name,
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Column(
                modifier = Modifier
                    .height(140.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                    )
                )
            }
            Counter()
            Button(onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.yellow)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.add_for) + " $${dish.price}",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                if (counter > 1) counter--
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.h2.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.h2.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.h2.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
