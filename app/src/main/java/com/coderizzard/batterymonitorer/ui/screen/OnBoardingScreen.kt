package com.coderizzard.batterymonitorer.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coderizzard.batterymonitorer.db.AppDatabase
import com.coderizzard.batterymonitorer.db.dao.RespondentDao
import com.coderizzard.batterymonitorer.ui.viewmodel.OnBoardingFormEvent
import com.coderizzard.batterymonitorer.ui.viewmodel.OnBoardingFormViewModel
import com.coderizzard.batterymonitorer.ui.viewmodel.RespondentStateViewModel
import com.coderizzard.batterymonitorer.ui.viewmodel.TextFormField

@Composable
fun OnBoardingScreen(
    navController: NavController,
    dao: RespondentDao,
    rpViewModel : RespondentStateViewModel
){
    val formViewModel : OnBoardingFormViewModel = viewModel()
    val formState by formViewModel.formState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(text="Registration", fontSize = 24.sp)

                TextFormField(
                    fieldData = formState.firstName,
                    label = {
                        Text("First Name")
                    },
                    onValueChange = {
                        formViewModel.onEvent(OnBoardingFormEvent.FirstNameChanged(it))
                    },
                )

                TextFormField(
                    fieldData = formState.lastName,
                    label = {
                        Text("Last Name")
                    },
                    onValueChange = {
                        formViewModel.onEvent(OnBoardingFormEvent.LastNameChanged(it))
                    },
                )
                TextFormField(
                    fieldData = formState.program,
                    label = {
                        Text("Your Program")
                    },
                    onValueChange = {
                        formViewModel.onEvent(OnBoardingFormEvent.ProgramChanged(it))
                    },
                )
                TextFormField(
                    fieldData = formState.year,
                    label = {
                        Text("Year")
                    },
                    onValueChange = {
                        formViewModel.onEvent(OnBoardingFormEvent.YearChanged(it))
                    },
                )

                TextFormField(
                    fieldData = formState.section,
                    label = {
                        Text("Your section")
                    },
                    onValueChange = {
                        formViewModel.onEvent(OnBoardingFormEvent.SectionChanged(it))
                    },
                )

                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {formViewModel.onEvent(OnBoardingFormEvent.FormSubmit(
                        navController,
                        dao,
                        rpViewModel
                    ))}) {
                    Text("Submit")
                }


            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    val context = LocalContext.current
    OnBoardingScreen(
        rememberNavController(),
        dao = AppDatabase.getDatabase(context).respondentDao(),
        viewModel(),
    )
}