package com.example.alarms

import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var minuteOfHourg: String? = null
    var hourOfDayg: String? = null
    var calendar: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timeInMilliSeconds: Long = 0
        val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)

        applicationContext.packageManager?.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        )

        startTimeText.setOnClickListener {
            // Get Current Time
            calendar = Calendar.getInstance()
            val hour = calendar!!.get(Calendar.HOUR_OF_DAY)
            val minute = calendar!!.get(Calendar.MINUTE)

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                calendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar!!.set(Calendar.MINUTE, minuteOfHour)
                calendar!!.set(Calendar.SECOND, 0)

//                val amPm = if (hourOfDay < 12) "am"
//                else
//                    "pm"
                minuteOfHourg = minuteOfHour.toString()
                hourOfDayg = hourOfDay.toString()
                val formattedTime = String.format("%02d:%02d", hourOfDay, minuteOfHour)
                startTimeText.text = formattedTime

                val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                val formattedDate = sdf.format(calendar!!.time)
                val date = sdf.parse(formattedDate)
                timeInMilliSeconds = date.time
            }, hour, minute, true)
            timePickerDialog.show()
        }

        setAlarm.setOnClickListener {
            if (timeInMilliSeconds.toInt() != 0) {
                Toast.makeText(this, "Alarm has been set!", Toast.LENGTH_LONG).show()

                val sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                        ?: return@setOnClickListener
//                with(sharedPref.edit()) {
//                    putLong("timeInMilli", timeInMilliSeconds)
//                    apply()
//                }

                //      NotificationHelper.scheduleRepeatingRTCNotification(this, hourOfDayg, minuteOfHourg)
                //  NotificationHelper.enableBootReceiver(this)
//

                var min: Int = minuteOfHourg!!.toInt()
                calendar?.let { it1 -> Utils.setAlarmNew(this, it1, 1) }
                calendar?.let { it1 -> Utils.setAlarmNew(this, it1, 2) }
                calendar?.let { it1 -> Utils.setAlarmNew(this, it1, 3) }

//                Utils.setAlarmNew(this, hourOfDayg, (min + 1).toString(), 2)
//                Utils.setAlarmNew(this, hourOfDayg, (min + 2).toString(), 3)
//                Utils.setAlarm(this, timeInMilliSeconds, 1)

//                Utils.setAlarm(this, timeInMilliSeconds, 2)
            } else {
                Toast.makeText(this, "Please enter the time first!", Toast.LENGTH_LONG).show()
            }
        }


    }


}
