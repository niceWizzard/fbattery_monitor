package com.coderizzard.batterymonitorer.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.coderizzard.batterymonitorer.db.entity.Respondent

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    var respondents by remember { mutableStateOf(emptyList<Respondent>()) }

    Row(modifier = modifier) {
        if(isLoading)
            Text("Loading data...")
        else
            for(resp in respondents) {
                Text(resp.toString())
            }
    }
}