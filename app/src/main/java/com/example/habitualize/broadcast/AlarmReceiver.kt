package com.example.habitualize.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.habitualize.ui.notification.NotificationUtils
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.isNotificationAllow

class AlarmReceiver : BroadcastReceiver()  {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if(SharePrefHelper.readBoolean(isNotificationAllow)){
            val notificationUtils = NotificationUtils(context)
            val notification = notificationUtils
                .getNotificationBuilder(
                    intent.getStringExtra("title").orEmpty(),
                    intent.getStringExtra("body").orEmpty()
                ).build()
            notificationUtils.getManager().notify(150, notification)
        }
    }
}