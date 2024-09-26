package com.coderizzard.batterymonitorer.service

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.Date

class BatteryInfoWatcher(private val context: Context) {
    private var _currentInfo = BatteryInfo(0, false, LocalDateTime.now(), -1)

    private var _isRunning = false

    fun isRunning(): Boolean {
        return _isRunning
    }
    fun start() : Flow<BatteryInfo> = flow {
        this@BatteryInfoWatcher._isRunning = true
        while(_isRunning) {
            val batteryInfo = getCurrentBatteryInfo()
            _currentInfo = batteryInfo
            emit(batteryInfo)
            delay(5000)
        }
    }

    fun stop() {
        _isRunning = false
    }


    private fun getCurrentBatteryInfo(): BatteryInfo {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            this@BatteryInfoWatcher.context.registerReceiver(null, ifilter)
        }
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL
        val batteryPct: Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            (level * 100 / scale.toFloat())
        }
        val temperature = batteryStatus?.let {intent: Intent ->
            intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10
        } ?: -1
        return BatteryInfo(
            batteryPct?.toInt() ?: -1,
            isCharging,
            LocalDateTime.now(),
            temperature
        )
    }




}

data class BatteryInfo(
    val percentage : Int,
    val isCharging : Boolean,
    val timestamp : LocalDateTime,
    val temperature : Int,
)