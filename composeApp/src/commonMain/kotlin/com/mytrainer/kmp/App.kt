package com.mytrainer.kmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mytrainer.kmp.navigation.MyTrainerNavHost
import com.mytrainer.kmp.ui.theme.MyTrainerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MyTrainerTheme {
        Scaffold { padding ->
            val navController = rememberNavController()
            Surface(modifier = Modifier.fillMaxSize()) {
                MyTrainerNavHost(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
        }
    }
}