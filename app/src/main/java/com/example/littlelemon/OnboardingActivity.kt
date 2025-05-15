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
import androidx.compose.ui.Modifier
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.Key
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.focus.FocusDirection

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                OnboardingScreen()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnboardingScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarText by remember { mutableStateOf("Enter all necessary information!") }

    fun validateAndRegister() {
        val emailPattern = Regex("^[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,3}$")
        val namePattern = Regex("^[a-zA-Z]+\$")

        when {
            firstName.isBlank() || lastName.isBlank() || email.isBlank() -> {
                snackbarText = "Enter all necessary information!"
                showSnackbar = true
            }
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
                val sharedPrefs = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
                with(sharedPrefs.edit()) {
                    putBoolean("is_registered", true)
                    putString("first_name", firstName)
                    putString("last_name", lastName)
                    putString("email", email)
                    apply()
                }
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as? ComponentActivity)?.finish()
            }
        }
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            delay(1500)
            showSnackbar = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp)) {
        TopAppBar()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(colorResource(id = R.color.green))
                .padding(16.dp)
        ) {
            Text(
                text = "Let's get to know you",
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
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                imeAction = ImeAction.None
            ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = colorResource(id = R.color.black),
                unfocusedIndicatorColor = colorResource(id = R.color.black)
            )
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
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                imeAction = ImeAction.None
            ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = colorResource(id = R.color.black),
                unfocusedIndicatorColor = colorResource(id = R.color.black)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Email", style = MaterialTheme.typography.subtitle1)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    if (it.key == Key.Enter) {
                        keyboardController?.hide()
                        validateAndRegister()
                        true
                    } else if (it.key == Key.Tab) {
                        focusManager.moveFocus(FocusDirection.Next)
                        true
                    } else false
                },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                validateAndRegister()
            }),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = colorResource(id = R.color.black),
                unfocusedIndicatorColor = colorResource(id = R.color.black)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            if (showSnackbar) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD32F2F), shape = RoundedCornerShape(6.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = snackbarText,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { validateAndRegister() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.yellow),
                contentColor = colorResource(id = R.color.black)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Register",
                fontSize = 18.sp
            )
        }
    }
}
