package com.aou.citysage.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aou.citysage.ui.theme.CitySageTheme
import com.uni.sehhaty.Utilitie.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CitySageTheme {
                AppNavigation()
            }
        }
    }
}