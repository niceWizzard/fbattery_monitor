package com.coderizzard.batterymonitorer.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.coderizzard.batterymonitorer.db.entity.BtPercentage
import com.coderizzard.batterymonitorer.service.AppService
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
            val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm:ss")
            items(percentageList, itemContent = { btPercentage ->
                val date = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(btPercentage.timestamp),
                    TimeZone.getDefault().toZoneId()
                ).let {
                    formatter.format(it)
                }
                Text("${btPercentage.percentage}% - at $date")
            })
        }
    }

}