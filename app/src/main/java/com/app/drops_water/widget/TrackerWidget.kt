package com.app.drops_water.widget

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import com.app.drops_water.data.preferences.DefaultPreferences
import com.app.drops_water.data.preferences.Preferences as AppPreferences

object TrackerWidget: GlanceAppWidget() {

    const val KEY_WIDGET_COUNT = "count"

    val countKey = intPreferencesKey(KEY_WIDGET_COUNT)

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val preferences = DefaultPreferences(
            context.getSharedPreferences(
                AppPreferences.PREF_NAME,
                ComponentActivity.MODE_PRIVATE
            )
        )


        provideContent {
            val count = currentState(key = countKey) ?: preferences.getCount()

            if (preferences.isSetUp()) {

                val localGoal = preferences.getGoal()
                val countPercent = count * 10 / localGoal

                context.WidgetContent(count = count, countPercent = countPercent)

            } else {
                context.WidgetEmptyContent()
            }
        }
    }


}


class CounterWidgetReceiver: GlanceAppWidgetReceiver() {
    
    override val glanceAppWidget: GlanceAppWidget
        get() = TrackerWidget

}


