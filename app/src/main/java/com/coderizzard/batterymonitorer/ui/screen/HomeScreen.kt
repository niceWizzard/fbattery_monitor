package com.coderizzard.batterymonitorer.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.coderizzard.batterymonitorer.db.entity.Respondent
import com.coderizzard.batterymonitorer.service.AppService

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    var respondents by remember { mutableStateOf(emptyList<Respondent>()) }
    val context = LocalContext.current

    Column(modifier) {
        ElevatedButton(onClick = {
            Intent(context, AppService::class.java).also {
                it.action = "start"
                context.startService(it)
            }
        }) {
            Text("Start Service")
        }
        ElevatedButton(onClick = {
            Intent(context, AppService::class.java).also {
                it.action = "stop"
                context.startService(it)
            }
        }) {
            Text("Stop Service")
        }
        if(isLoading)
            Text("Loading data...")
        else
            for(resp in respondents) {
                Text(resp.toString())
            }
    }
}