package com.example.alarms

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

/**
 * Created by ptyagi on 4/17/17.
 */

/**
 * AlarmReceiver handles the broadcast message and generates Notification
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //Get notification manager to manage/send notifications


        //Intent to invoke app when click on notification.
        //In this sample, we want to start/launch this sample app when user clicks on notification
        val intentToRepeat = Intent(context, MainActivity::class.java)
        //set flag to restart/relaunch the app
        intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        //Pending intent to handle launch of Activity in intent above
        val pendingIntent = PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)

        //Build notification
     //   val repeatedNotification = buildLocalNotification(context, pendingIntent).build()

        //Send local notification
       // NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Alarm"
            val descriptionText = "Alarm details"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("AlarmId", name, importance)
            mChannel.description = descriptionText
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        // Create the notification to be shown
        val mBuilder = NotificationCompat.Builder(context!!, "AlarmId")
                .setSmallIcon(com.example.alarms.R.mipmap.ic_launcher)
                .setContentTitle("Alarm")
                .setContentText(intent.getStringExtra("date"))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Get the Notification manager service
        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate an Id for each notification
        //   val id = System.currentTimeMillis() / 1000

        // Show a notification
        am.notify(intent!!.getIntExtra("id", 0), mBuilder.build())








    }


    fun buildLocalNotification(context: Context, pendingIntent: PendingIntent): NotificationCompat.Builder {

        return NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.arrow_up_float)
                .setContentTitle("Morning Notification")
                .setAutoCancel(true)
    }


}
