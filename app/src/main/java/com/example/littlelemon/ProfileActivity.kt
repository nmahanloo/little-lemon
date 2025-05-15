package com.example.littlelemon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.key
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.delay

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                ProfileScreen()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val sharedPrefs = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)

    var firstName by remember { mutableStateOf(sharedPrefs.getString("first_name", "") ?: "") }
    var lastName by remember { mutableStateOf(sharedPrefs.getString("last_name", "") ?: "") }
    var email by remember { mutableStateOf(sharedPrefs.getString("email", "") ?: "") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarText by remember { mutableStateOf("") }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            delay(1500)
            showSnackbar = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp)) {
        TopAppBar(onBackClick = { (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed() })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(colorResource(id = R.color.green))
                .padding(16.dp)
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.h5,
                color = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text("Personal Information", style = MaterialTheme.typography.h6, modifier = Modifier.padding(bottom = 50.dp))

        Text("First Name", style = MaterialTheme.typography.subtitle1)
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    when (it.key) {
                        Key.Enter -> true
                        Key.Tab -> {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        }
                        else -> false
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = colorResource(id = R.color.black),
                unfocusedIndicatorColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false, capitalization = KeyboardCapitalization.None),
            enabled = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Last Name", style = MaterialTheme.typography.subtitle1)
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    when (it.key) {
                        Key.Enter -> true
                        Key.Tab -> {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        }
                        else -> false
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = colorResource(id = R.color.black),
                unfocusedIndicatorColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false, capitalization = KeyboardCapitalization.None),
            enabled = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Email", style = MaterialTheme.typography.subtitle1)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    when (it.key) {
                        Key.Enter -> true
                        Key.Tab -> {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        }
                        else -> false
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = colorResource(id = R.color.black),
                unfocusedIndicatorColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false, capitalization = KeyboardCapitalization.None),
            enabled = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (showSnackbar) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD32F2F), shape = RoundedCornerShape(6.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = snackbarText,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val emailPattern = Regex("^[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,3}$")
                val namePattern = Regex("^[a-zA-Z]+\$")

                when {
                    !namePattern.matches(firstName) -> {
                        snackbarText = "First name must contain only letters!"
                        showSnackbar = true
                    }
                    !namePattern.matches(lastName) -> {
                        snackbarText = "Last name must contain only letters!"
                        showSnackbar = true
                    }
                    !emailPattern.matches(email) -> {
                        snackbarText = "Invalid email format!"
                        showSnackbar = true
                    }
                    else -> {
                        with(sharedPrefs.edit()) {
                            putString("first_name", firstName)
                            putString("last_name", lastName)
                            putString("email", email)
                            apply()
                        }
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as? ComponentActivity)?.finish()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.light_gray),
                contentColor = colorResource(id = R.color.green)
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally).wrapContentWidth().height(56.dp)
        ) {
            Text("Update", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(68.dp))

        Button(
            onClick = {
                sharedPrefs.edit().clear().apply()
                context.startActivity(Intent(context, OnboardingActivity::class.java))
                (context as? ComponentActivity)?.finish()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.yellow),
                contentColor = colorResource(id = R.color.black)
            ),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Log out", fontSize = 18.sp)
        }
    }
}
