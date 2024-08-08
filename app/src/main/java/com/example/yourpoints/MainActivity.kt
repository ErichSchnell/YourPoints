package com.example.yourpoints

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yourpoints.presentation.core.ContentWrapper
import com.example.yourpoints.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    navigationController = rememberNavController()
                    ContentWrapper(navigationController)
                }
            }
        }
    }
}




@Composable
fun typografics(){
    AppTheme{
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "displayLarge", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displayLarge)
            Text(text = "displayMedium", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displayMedium)
            Text(text = "displaySmall", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "headlineLarge", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineLarge)
            Text(text = "headlineMedium", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineMedium)
            Text(text = "headlineSmall", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "titleLarge", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleLarge)
            Text(text = "titleMedium", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
            Text(text = "titleSmall", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "bodyLarge", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyLarge)
            Text(text = "bodyMedium", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyMedium)
            Text(text = "bodySmall", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "labelLarge", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelLarge)
            Text(text = "labelMedium", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelMedium)
            Text(text = "labelSmall", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelSmall)
        }
    }
}