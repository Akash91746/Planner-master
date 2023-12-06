package com.example.planner.common.data.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.example.planner.common.domain.services.AlarmService

class AlarmServiceImpl(
    context: Context,
) : AlarmService {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(
        triggerAtMillis: Long,
        pendingIntent: PendingIntent,
    ) {
        return alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(pendingIntent: PendingIntent) {
        return alarmManager.cancel(pendingIntent)
    }
}