package com.uni.sehhaty.Utilitie

import android.R
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aou.citysage.CitySageScreen
import com.aou.citysage.LoginScreen
import com.aou.citysage.LoginViewModel
import com.aou.citysage.Screens


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    //val myViewModel = remember { MyViewModel() } // Single instance


    val currentUser by loginViewModel.currentUser.collectAsState()

    // Auto-navigate if user is already logged in
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate(Screens.CitySagePage) {
                popUpTo<Screens.LoginPage> { inclusive = true }
            }
        }
    }

    // Bottom Navigation Items
    val navItems = listOf(
        NavItem(
            route = Screens.ProfilePage,
            label = "Profile",
            icon = Icons.Default.Person
        ),
        NavItem(
            route = Screens.LoginPage,
            label = "Search",
            icon = Icons.Default.Search
        ),
        NavItem(
            route = Screens.AppointmentsPage,
            label = "Appointments",
            icon = Icons.Default.DateRange
        ),
        NavItem(
            route = Screens.LabPage,
            label = "LAB",
            icon = Icons.Default.DateRange
        ),
        NavItem(
            route = Screens.MedicinePage,
            label = "medicine",
            icon = Icons.Default.DateRange
        ),
    )

    Scaffold(
        bottomBar = {
            // Only show bottom bar if not on LoginPage
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute != Screens.LoginPage::class.qualifiedName) {
                NavigationBar {
                    navItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                when (item.icon) {
                                    is Int -> Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = item.label
                                    )
                                    is ImageVector -> Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.label
                                    )
                                }
                            },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route::class.qualifiedName,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.LoginPage,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screens.LoginPage> {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate(Screens.CitySagePage) {
                            popUpTo<Screens.LoginPage> { inclusive = true }
                        }
                    }
                )
            }
            composable<Screens.CitySagePage> {
                CitySageScreen()
            }







        }
    }
}

// Data class for navigation items
data class NavItem(
    val route: Any,
    val label: String,
    val icon: Any
)