package com.reactnativenotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import com.reactnativenotification.R

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

// TODO: Step 1.1 extension function to send messages (GIVEN)
/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String,title:String, applicationContext: Context) {
  // Create the content intent for the notification, which launches
  // this activity
  val packageName: String = applicationContext.packageName
  val launchIntent: Intent? = applicationContext.packageManager.getLaunchIntentForPackage(packageName)
  val className = launchIntent?.component?.className
  val classNameFinal = className?.let { Class.forName(it) }


  val contentIntent = Intent(applicationContext, classNameFinal)
  val contentPendingIntent = PendingIntent.getActivity(
    applicationContext,
    NOTIFICATION_ID,
    contentIntent,
    PendingIntent.FLAG_UPDATE_CURRENT
  )

  val notificationImage = BitmapFactory.decodeResource(
    applicationContext.resources,
   R.mipmap.app_icon_small
  )
  val bigPicStyle = NotificationCompat.BigPictureStyle()
    .bigPicture(notificationImage)
    .bigLargeIcon(null)

  // TODO: Step 2.2 add snooze action
  val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
  val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
    applicationContext,
    REQUEST_CODE,
    snoozeIntent,
    PendingIntent.FLAG_UPDATE_CURRENT)

  // get an instance of NotificationCompat.Builder
  // Build the notification
  val builder = NotificationCompat.Builder(
    applicationContext,
    applicationContext.getString(R.string.notification_channel_id)
  )


    //  title, text and icon to builder
    .setSmallIcon(R.mipmap.app_icon_small)
    .setContentTitle(title)
    .setContentText(messageBody)

    // TODO: Step 1.13 set content intent
    .setContentIntent(contentPendingIntent)
    .setAutoCancel(true)

    // TODO: Step 2.1 add style to builder
    .setStyle(bigPicStyle)
    .setLargeIcon(notificationImage)

    // TODO: Step 2.3 add snooze action
    .addAction(
      R.drawable.ic_android_black_24dp,
      "Snooze",
      snoozePendingIntent
    )

    // TODO: Step 2.5 set priority
    .setPriority(NotificationCompat.PRIORITY_HIGH)
  // TODO: Step 1.4 call notify
  notify(NOTIFICATION_ID, builder.build())

      Log.d("utils","showNotificatisons")

}

// TODO: Step 1.14 Cancel all notifications
/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
  cancelAll()
}
