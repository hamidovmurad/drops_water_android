package com.app.drops_water.widget

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import com.app.drops_water.data.preferences.DefaultPreferences
import com.app.drops_water.data.preferences.Preferences

class IncrementActionCallback: ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val preferences = DefaultPreferences(context.getSharedPreferences(
            Preferences.PREF_NAME,
            ComponentActivity.MODE_PRIVATE))

        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[TrackerWidget.countKey]

            currentCount?.let {
                if(currentCount == 11) {
                    prefs[TrackerWidget.countKey] = 0
                    preferences.setCount(0)
                }else {
                    preferences.setCount(currentCount + 1)
                    prefs[TrackerWidget.countKey] = currentCount + 1
                }
            }?: run{
                val newCount = preferences.getCount() + 1
                prefs[TrackerWidget.countKey] = newCount
                preferences.setCount( newCount)
            }

        }
        TrackerWidget.update(context, glanceId)
    }
}