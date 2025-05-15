// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UpperPanel(onSearchChanged: (String) -> Unit) {
    var searchPhrase by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.green))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .heightIn(max = 320.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.yellow)
        )
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 24.sp,
            color = colorResource(id = R.color.white)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)

        ) {
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(bottom = 28.dp, end = 20.dp)
                    .fillMaxWidth(0.6f)
            )
            Box(
                modifier = Modifier
                    .offset(y = (-50).dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.upperpanelimage),
                    contentDescription = "Upper Panel Image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
        TextField(
            value = searchPhrase,
            onValueChange = {
                searchPhrase = it
                onSearchChanged(it)
            },
            placeholder = { Text("Enter search phrase") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.light_gray)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-24).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UpperPanelPreview() {
    UpperPanel(onSearchChanged = {})
}
