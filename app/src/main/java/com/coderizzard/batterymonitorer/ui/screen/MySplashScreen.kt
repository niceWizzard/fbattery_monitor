package com.coderizzard.batterymonitorer.ui.screen

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coderizzard.batterymonitorer.NavRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun MySplashScreen(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        delay(500)
        navController.navigate(NavRoute.HOME)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Splash Screen", fontSize = 32.sp)
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        MySplashScreen(rememberNavController())
    }
}

