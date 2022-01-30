package com.reactnativenotification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.facebook.react.modules.core.DeviceEventManagerModule

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext

import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter


class AlarmReceiver: BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
     val title :String?= intent.extras?.get("title") as? String
    val body :String? = intent.extras?.get("body") as? String
    val notificationManager = ContextCompat.getSystemService(
      context,
      NotificationManager::class.java
    ) as NotificationManager
    Log.d("alarmReceiver",intent.toString())
    notificationManager.sendNotification(
      body ?: context.getText(R.string.notification_ready).toString(),
      title ?: "Notification",
      context
    )


  }

}
