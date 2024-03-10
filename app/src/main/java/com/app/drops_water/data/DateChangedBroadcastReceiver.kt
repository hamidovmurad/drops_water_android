package com.app.drops_water.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.app.drops_water.data.preferences.DefaultPreferences
import com.app.drops_water.data.preferences.Preferences
import com.app.drops_water.widget.TrackerWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

internal class DateChangedBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            val preferences = DefaultPreferences(
                it.getSharedPreferences(
                    Preferences.PREF_NAME,
                    ComponentActivity.MODE_PRIVATE
                )
            )

            CoroutineScope(Dispatchers.IO).launch {
                GlanceAppWidgetManager(it)
                    .getGlanceIds(TrackerWidget::class.java).firstOrNull()?.let { glanceId ->
                        updateAppWidgetState(it, glanceId) { prefs ->
                            prefs[intPreferencesKey(TrackerWidget.KEY_WIDGET_COUNT)] = 0
                        }
                        TrackerWidget.update(it, glanceId)
                    }
            }

            preferences.setCount(0)
            it.scheduleAlarm()

        }

    }

}



fun Context.scheduleAlarm() {

    val INTERVAL = getMillisecondsTillNextMidnight()
    val UNIQUE_REQUEST_CODE =  1000

    val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(this, DateChangedBroadcastReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        this,
        UNIQUE_REQUEST_CODE,
        alarmIntent,
        PendingIntent.FLAG_IMMUTABLE
    )


    // Set up the alarm
    alarmManager.set(
        AlarmManager.RTC_WAKEUP,
        INTERVAL,
        pendingIntent
    )
}


fun getMillisecondsTillNextMidnight(): Long {

    val now = Calendar.getInstance()
    val alarm = Calendar.getInstance()
    alarm[Calendar.HOUR_OF_DAY] = 0
    alarm[Calendar.MINUTE] = 0
    if (alarm.before(now)) alarm.add(Calendar.DAY_OF_MONTH, 1)

    return alarm.timeInMillis
}