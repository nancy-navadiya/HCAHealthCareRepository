package com.example.hcahealthcaretask.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hcahealthcaretask.view.compose.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
/** Hosts the Compose UI and provides the Hilt entry point for the application. */
class MainActivity : ComponentActivity() {

    /** Applies the app theme and installs the navigation graph as the activity content. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}