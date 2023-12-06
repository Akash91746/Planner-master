package com.example.planner.broadcasts

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.planner.R
import com.example.planner.workers.TaskDoneWorker

class TaskDoneBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        intent.extras?.let { bundle ->
            val taskId = bundle.getInt(context.getString(R.string.task_id_key), Int.MAX_VALUE)

            if (taskId != Int.MAX_VALUE ) {
                val data = Data.Builder()
                data.putInt(context.getString(R.string.task_id_key), taskId)

                taskDoneWorker(context,data)
            }
        }
    }

    private fun taskDoneWorker(context: Context, dataBuilder: Data.Builder) {
        val taskDoneWorker = OneTimeWorkRequestBuilder<TaskDoneWorker>()
            .setInputData(dataBuilder.build())

        WorkManager.getInstance(context).enqueue(taskDoneWorker.build())
    }

    companion object {

        fun getPendingIntent(
            context: Context,
            id: Int,
            flag: Int = PendingIntent.FLAG_IMMUTABLE,
        ): PendingIntent {

            val intent = Intent(context, TaskDoneBroadcastReceiver::class.java)

            intent.putExtra(context.getString(R.string.task_id_key), id)

            return PendingIntent.getBroadcast(context, id, intent, flag)
        }

    }
}