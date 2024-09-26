package com.coderizzard.batterymonitorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coderizzard.batterymonitorer.db.AppDatabase
import com.coderizzard.batterymonitorer.ui.screen.HomeScreen
import com.coderizzard.batterymonitorer.ui.theme.BatteryMonitorerTheme
import com.coderizzard.batterymonitorer.ui.viewmodel.HomeScreenEvent
import com.coderizzard.batterymonitorer.ui.viewmodel.HomeScreenViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        AppDatabase.getDatabase(this@MainActivity)
    }

    private val viewModel by viewModels<HomeScreenViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return HomeScreenViewModel(dao=db.btPercetageDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatteryMonitorerTheme {
                viewModel.onEvent(HomeScreenEvent.RefetchLatestPercentage)
                val percentageList by viewModel.latestPercentageInfo.collectAsState(emptyList())
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Row(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController, startDestination = NavRoute.HOME
                        ) {
                            composable(route = NavRoute.HOME) {
                                HomeScreen(percentageList)
                            }
                        }
                    }
                }
            }
        }
    }
}

object NavRoute {
    const val HOME = "home"
}
