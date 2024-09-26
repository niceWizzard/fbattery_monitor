package com.coderizzard.batterymonitorer.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.coderizzard.batterymonitorer.db.entity.BtPercentage
import com.coderizzard.batterymonitorer.db.entity.Respondent
import com.coderizzard.batterymonitorer.service.AppService
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.TimeZone

@Composable
fun HomeScreen(percentageList : List<BtPercentage>) {
    val context = LocalContext.current

    Column() {
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
        LazyColumn {
            items(percentageList, itemContent = {
                Text("${it.percentage}% - at ${LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(it.timestamp),
                    TimeZone.getDefault().toZoneId()
                )}")
            })
        }
    }

}