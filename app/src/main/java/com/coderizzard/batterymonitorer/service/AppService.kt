package com.coderizzard.batterymonitorer.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import com.coderizzard.batterymonitorer.App
import com.coderizzard.batterymonitorer.MainActivity
import com.coderizzard.batterymonitorer.R
import com.coderizzard.batterymonitorer.db.AppDatabase
import com.coderizzard.batterymonitorer.db.entity.BtPercentage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AppService : Service() {

    private lateinit var db : AppDatabase
    private val batteryInfoWatcher = BatteryInfoWatcher(this)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this@AppService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            "start" -> start()
            "stop" -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        if(batteryInfoWatcher.isRunning()) {return}
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        CoroutineScope(Dispatchers.Default).launch {
            batteryInfoWatcher.start().collect {
                Log.d("Battery Info", it.toString())
                createNotification(it, pendingIntent)
                saveToDb(it)
            }
        }
    }

    private fun saveToDb(info: BatteryInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = db.btPercetageDao()
            dao.create(
                BtPercentage(
                    timestamp = info.timestamp.time,
                    respondentId = 1,
                    percentage = info.percentage,
                    id = 0
                )
            )
        }
    }


    private fun createNotification(info : BatteryInfo, contentIntent: PendingIntent) {
        val content = "${info.percentage}% | ${if(info.isCharging) "Charging" else "Discharging"}"
        val notif = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Battery Monitor - ${info.percentage}%")
            .setContentText(content)
            .setStyle(
                BigTextStyle().bigText("$content \nWe are monitoring your battery usage. Thank you. \n-BSMCS 3A")

            )
            .setContentIntent(contentIntent)
            .build()
        startForeground(1, notif)
    }

    private fun stop() {
        batteryInfoWatcher.stop()
        stopSelf()
    }

}