package com.coderizzard.batterymonitorer.ui.screen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coderizzard.batterymonitorer.NavRoute
import com.coderizzard.batterymonitorer.ui.viewmodel.RespondentStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun MySplashScreen(
    navController: NavController,
    respondentStateViewModel: RespondentStateViewModel,
    activity : Activity
) {
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
        if(respondentStateViewModel.respondent != null) {
            navController.navigate(NavRoute.HOME)
        } else {
            navController.navigate(NavRoute.ONBOARDING)
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Splash Screen", fontSize = 32.sp)
    }
}



