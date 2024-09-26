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
import androidx.compose.material3.Text
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
import com.coderizzard.batterymonitorer.ui.screen.MySplashScreen
import com.coderizzard.batterymonitorer.ui.screen.OnBoardingScreen
import com.coderizzard.batterymonitorer.ui.theme.BatteryMonitorerTheme
import com.coderizzard.batterymonitorer.ui.viewmodel.HomeScreenEvent
import com.coderizzard.batterymonitorer.ui.viewmodel.HomeScreenViewModel
import com.coderizzard.batterymonitorer.ui.viewmodel.RespondentStateViewModel

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

    private val respondentStateViewModel by viewModels<RespondentStateViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val respList =  db.respondentDao().getRespondents()
                    val respondent = if( respList.isEmpty()) null else respList[0]
                    return RespondentStateViewModel(respondent) as T
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
                            navController = navController, startDestination = NavRoute.SPLASH
                        ) {
                            composable(route = NavRoute.HOME) {
                                HomeScreen(percentageList)
                            }
                            composable(route = NavRoute.SPLASH) {
                                MySplashScreen(
                                    navController = navController,
                                    respondentStateViewModel = respondentStateViewModel,
                                    activity = this@MainActivity
                                )
                            }
                            composable(route = NavRoute.ONBOARDING) {
                                OnBoardingScreen(
                                    navController=navController,
                                    dao = db.respondentDao(),
                                    rpViewModel = respondentStateViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

object NavRoute {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val ONBOARDING="onboarding"
}
