// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(onBackClick: (() -> Unit)? = null) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
    }
    val firstName = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        firstName.value = sharedPreferences.getString("first_name", "") ?: ""
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            if (firstName.value.isNotBlank() && onBackClick == null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            context.startActivity(Intent(context, ProfileActivity::class.java))
                        }
                )
            } else {
                Spacer(modifier = Modifier.size(28.dp))
            }
        }

        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.size(180.dp)
        )
    }
}

