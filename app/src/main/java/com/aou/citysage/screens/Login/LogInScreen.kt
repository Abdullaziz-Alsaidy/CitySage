package com.aou.citysage.screens.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.aou.citysage.LoginViewModel
import com.aou.citysage.UiState


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
)
{
    val loginState by viewModel.loginState.collectAsState()
    val focusManager = LocalFocusManager.current

    // Handle success state
    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email Field
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("You Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = viewModel::updatePassword,
            label = { Text("*******") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        // Login Button
        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.loginUser()

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState != UiState.Loading
        ) {
            if (loginState == UiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login")
            }
        }

        // Profile Dialog
        if (viewModel.showProfileDialog) {
            AlertDialogExample(
                onDismissRequest = { /* Prevent dismissal */ },
                onConfirmation = {
                    viewModel.saveUserProfile()
                },
                dialogTitle = "Complete Your Profile",
                dialogText = "Please provide your profile information",
                icon = Icons.Default.Info,
                isConfirmEnabled = viewModel.firstName.isNotBlank() &&
                        viewModel.lastName.isNotBlank() &&
                        viewModel.phone.isNotBlank(),

            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.firstName,
                        onValueChange = viewModel::updateFirstName,
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = viewModel.lastName,
                        onValueChange = viewModel::updateLastName,
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = viewModel.phone,
                        onValueChange = viewModel::updatePhone,
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Error Message
        if (loginState is UiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (loginState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    isConfirmEnabled: Boolean,
    content: @Composable () -> Unit
)
{
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column {
                Text(text = dialogText)
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmation,
                enabled = isConfirmEnabled
            ) {
                Text("Confirm")
            }
        },
        dismissButton = null
    )
}




data class Place(
    val name :String= "details 1 ",
    val icon: String= "details 1 ",
    val details: String,
    val distance :String= "details 1 ",
    val rating: String= "details 1 ",
    val free: String= "details 1 ",
    val times : String= "details 1 "
)
//Card(
//modifier = Modifier
//.fillMaxWidth()
//.padding(vertical = 8.dp),
//shape = RoundedCornerShape(12.dp)