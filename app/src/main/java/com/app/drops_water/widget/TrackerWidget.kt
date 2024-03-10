package com.app.drops_water.widget

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.app.drops_water.R
import com.app.drops_water.data.preferences.DefaultPreferences
import com.app.drops_water.presentation.DisplayWaterImage
import com.app.drops_water.ui.theme.Black
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.White
import com.app.drops_water.data.preferences.Preferences as AppPreferences

object TrackerWidget: GlanceAppWidget() {

    val countKey = intPreferencesKey("count")

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val preferences = DefaultPreferences(context.getSharedPreferences(AppPreferences.PREF_NAME,
            ComponentActivity.MODE_PRIVATE))

        provideContent {
            if (preferences.isSetUp()) {

                val localGoal = preferences.getGoal()
                val currentNumber = currentState(key = countKey) ?: preferences.getCurrent()
                val count = currentNumber * 10 / localGoal

                Column(
                    modifier = GlanceModifier
                        .fillMaxSize().padding(4.dp)
                        .background(White),
                    verticalAlignment = Alignment.Vertical.CenterVertically,
                    horizontalAlignment = Alignment.Horizontal.CenterHorizontally
                ) {


                    Button(
                        text = context.getString(R.string.add_1),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ColorProvider(Blue),
                            ColorProvider(White)
                        ),
                        onClick = actionRunCallback(IncrementActionCallback::class.java)
                    )

                    Spacer(modifier = GlanceModifier.height(12.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {


                        if (count > 0) {
                            val img = DisplayWaterImage(count)
                            Image(
                                provider = ImageProvider(img),
                                contentDescription = "water",
                            )
                        }

                        Image(
                            provider = ImageProvider(R.drawable.ic_glass),
                            contentDescription = "glass"
                        )


                        Text(
                            text = currentNumber.toString(),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = ColorProvider(Black),
                                fontSize = 28.sp
                            )
                        )

                    }
                }

            }else{
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize().padding(16.dp)
                        .background(Blue),
                    verticalAlignment = Alignment.Vertical.CenterVertically,
                    horizontalAlignment = Alignment.Horizontal.CenterHorizontally
                ) {
                    Text(
                        text = context.getString(R.string.goal_not_set),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            color = ColorProvider(White),
                            fontSize = 22.sp,
                        )
                    )
                    Image(
                        provider = ImageProvider(R.mipmap.ic_launcher_foreground),
                        contentDescription = "logo"
                    )

                }
            }
        }
    }


}


class CounterWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = TrackerWidget
}


class IncrementActionCallback: ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val preferences = DefaultPreferences(context.getSharedPreferences(AppPreferences.PREF_NAME,
            ComponentActivity.MODE_PRIVATE))

        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[TrackerWidget.countKey]

            currentCount?.let {
                if(currentCount == 11) {
                    prefs[TrackerWidget.countKey] = 0
                    preferences.setCurrent( 0)
                }else {
                    preferences.setCurrent(currentCount + 1)
                    prefs[TrackerWidget.countKey] = currentCount + 1
                }
            }?: run{
                val newCount = preferences.getCurrent() + 1
                prefs[TrackerWidget.countKey] = newCount
                preferences.setCurrent( newCount)
            }

        }
        TrackerWidget.update(context, glanceId)
    }
}
