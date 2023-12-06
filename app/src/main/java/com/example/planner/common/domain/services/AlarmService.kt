package com.example.planner.common.domain.services

import android.app.PendingIntent

interface AlarmService {

    fun schedule(
        triggerAtMillis: Long,
        pendingIntent: PendingIntent
    )
    fun cancel(pendingIntent: PendingIntent)

}