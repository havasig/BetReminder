package hu.havasig.betreminder.android.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import hu.havasig.betreminder.Auth
import hu.havasig.betreminder.android.AndroidUserViewModel
import hu.havasig.betreminder.android.navigation.Screens
import hu.havasig.betreminder.presenter.KMPUserPresenter
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AndroidUserViewModel = koinViewModel(),
    presenter: KMPUserPresenter = get(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var email by rememberSaveable { mutableStateOf("asd@asd.asd") }
    var password by rememberSaveable { mutableStateOf("asdasd") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password") },

            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Menu
                else Icons.Filled.Add

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
        Button(onClick = {
            scope.launch {
                try {
                    Auth().createUser(email, password)
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.Home.route)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }) {
            Text(text = "register")
        }
        Button(onClick = {
            scope.launch {
                try {
                    Auth().loginUser(email, password)
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.Home.route)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    Log.e("LOGIN_ERROR", e.message ?: "login error")
                }
            }
        }) {
            Text(text = "login")
        }
        Text(text = presenter.sayHello() + "\n\n" + viewModel.sayHello() + "\n\n")
    }
}
