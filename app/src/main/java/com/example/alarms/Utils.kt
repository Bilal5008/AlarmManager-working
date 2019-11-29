package com.example.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import com.example.alarms.NotificationHelper.ALARM_TYPE_RTC
import java.util.*

object Utils {
    var ALARM_TYPE_RTC = 100
    private var alarmManagerRTC: AlarmManager? = null
    private var alarmIntentRTC: PendingIntent? = null


    fun setAlarm(context: Context, timeOfAlarm: Long, i: Int) {

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(context, NotificationReceiver::class.java)
        broadcastIntent.putExtra("id", i)
        val pIntent = PendingIntent.getBroadcast(
                context,
                i,
                broadcastIntent,
                0
        )

        // Setting up AlarmManager
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //   if (System.currentTimeMillis() < timeOfAlarm) {
        alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                timeOfAlarm,
                pIntent
        )


        // }
    }

    fun setAlarmNew(mainActivity: Context, calendar: Calendar, i: Int) {



        //Setting intent to class where Alarm broadcast message will be handled

        val broadcastIntent = Intent(mainActivity, AlarmReceiver::class.java)
        broadcastIntent.putExtra("id", i)
       // broadcastIntent.putExtra("date", hourOfDayg.toString() + ":" + minuteOfHourg.toString())

        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(mainActivity, i, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //getting instance of AlarmManager service
        alarmManagerRTC = mainActivity.getSystemService(ALARM_SERVICE) as AlarmManager

        //Setting alarm to wake up device every day for clock time.
        //AlarmManager.RTC_WAKEUP is responsible to wake up device for sure, which may not be good practice all the time.
        // Use this when you know what you're doing.
        //Use RTC when you don't need to wake up device, but want to deliver the notification whenever device is woke-up
        //We'll be using RTC.WAKEUP for demo purpose only
        //  alarmManagerRTC!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, alarmIntentRTC)
//        alarmManagerRTC!!.set(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                alarmIntentRTC
//        )

        alarmManagerRTC!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, alarmIntentRTC)

    }
}