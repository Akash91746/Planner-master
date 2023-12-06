package com.example.planner.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.planner.workers.SetAllRepeatTaskReminder

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null &&
            intent.action != null &&
            intent.action.equals(Intent.ACTION_BOOT_COMPLETED)
        ) {
            val setUpReminderWorker = OneTimeWorkRequestBuilder<SetAllRepeatTaskReminder>()
            WorkManager.getInstance(context).enqueue(setUpReminderWorker.build())
        }
    }
}