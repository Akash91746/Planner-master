package com.example.planner.broadcasts

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.planner.R
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.workers.TaskNotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskNotificationBroadcast : BroadcastReceiver() {

    @Inject
    lateinit var plannerDatabase: PlannerDatabase

    override fun onReceive(context: Context, intent: Intent) {

        intent.extras?.let { bundle ->

            val taskId = bundle.getInt(context.getString(R.string.task_id_key), Int.MAX_VALUE)

            if (taskId != Int.MAX_VALUE) {
                val data = Data.Builder()
                data.putInt(context.getString(R.string.task_id_key), taskId)

                showNotification(context, data)
            }
        }
    }

    private fun showNotification(context: Context, dataBuilder: Data.Builder) {

        val notificationWorker = OneTimeWorkRequestBuilder<TaskNotificationWorker>()
            .setInputData(dataBuilder.build())

        WorkManager.getInstance(context).enqueue(notificationWorker.build())
    }


    companion object {

        fun getPendingIntent(
            context: Context,
            id: Int,
            flag: Int = PendingIntent.FLAG_IMMUTABLE,
        ): PendingIntent {

            val intent = Intent(context, TaskNotificationBroadcast::class.java)
            intent.putExtra(context.getString(R.string.task_id_key), id)

            return PendingIntent.getBroadcast(context, id, intent, flag)
        }

    }


}