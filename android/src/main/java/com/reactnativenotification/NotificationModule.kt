package com.reactnativenotification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.reactnativenotification.sendNotification

class NotificationModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext),ActivityEventListener {
  private val REQUEST_CODE = 0
  private var notifyPendingIntent: PendingIntent? = null
    override fun getName(): String {
        return "Notification"
    }


  private val notifyIntent = Intent(reactContext, AlarmReceiver::class.java)
  val alarmManager = reactApplicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

  private fun createChannel(channelId: String, channelName: String) {
    // TODO: Step 1.6 START create a channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationChannel = NotificationChannel(
        channelId,
        channelName,
        // TODO: Step 2.4 change importance
        NotificationManager.IMPORTANCE_HIGH
      )// TODO: Step 2.6 disable badges for this channel
        .apply {
          setShowBadge(false)
        }

      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.RED
      notificationChannel.enableVibration(true)
      notificationChannel.description = reactApplicationContext.getString(R.string.channel_description)

      val notificationManager = (reactApplicationContext.getSystemService(
        NotificationManager::class.java
      ))
      notificationManager.createNotificationChannel(notificationChannel)

    } }
    // TODO: Step 1.6 END create a channel
  @ReactMethod
  fun showNotification(title:String,text:String, triggerTime:Int){
      // Create map for params
      val payload = Arguments.createMap()
      // Put data to map
      payload.putString("testing_events", "I am Testing Events")
      // Get EventEmitter from context and send event thanks to it
      reactApplicationContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("onTestingEvents", payload)
   createChannel(
     reactApplicationContext.getString(R.string.notification_channel_id),
     reactApplicationContext.getString(R.string.notification_channel_name))
    val triggTime = SystemClock.elapsedRealtime() + triggerTime.toLong()
    val notificationManager =
      ContextCompat.getSystemService(
        reactApplicationContext,
        NotificationManager::class.java
      ) as NotificationManager
    notificationManager.cancelNotifications()

     Log.d("Notific","showNotifications")



      notifyIntent.putExtras(Bundle().apply {
        putString("title",title)
        putString("body",text)
      })
      notifyPendingIntent = PendingIntent.getBroadcast(
        reactApplicationContext,
        REQUEST_CODE,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
      )

      notifyPendingIntent?.let {
        AlarmManagerCompat.setExactAndAllowWhileIdle(
          alarmManager,
          AlarmManager.ELAPSED_REALTIME_WAKEUP,
          triggTime,
          it
        )
      }
  }
    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    fun multiply(a: Int, b: Int, promise: Promise) {

      promise.resolve(a * b)

    }

  override fun onActivityResult(p0: Activity?, p1: Int, p2: Int, p3: Intent?) {
  }

  override fun onNewIntent(p0: Intent?) {

  }


}
