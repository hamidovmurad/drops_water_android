package com.app.drops_water.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.ButtonColors
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
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
import com.app.drops_water.ui.theme.Black
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.White

object CounterWidget: GlanceAppWidget() {

    val countKey = intPreferencesKey("count")

    @Composable
    override fun Content() {
        val count = currentState(key = countKey) ?: 0


            Column(
                modifier = GlanceModifier
                    .fillMaxSize().padding(4.dp)
                    .background(White),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {


                Button(
                    text = "Add +1",
                    colors = ButtonColors(
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
                        val img = when (count) {
                            1 -> R.drawable.ic_water10
                            2 -> R.drawable.ic_water20
                            3 -> R.drawable.ic_water30
                            4 -> R.drawable.ic_water40
                            5 -> R.drawable.ic_water50
                            6 -> R.drawable.ic_water60
                            7 -> R.drawable.ic_water70
                            8 -> R.drawable.ic_water80
                            9 -> R.drawable.ic_water90
                            10 -> R.drawable.ic_water100
                            else -> R.drawable.ic_water100
                        }

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
                        text = count.toString(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Black),
                            fontSize = 28.sp
                        )
                    )

                }


            }

    }

}


class SimpleCounterWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget
}

class IncrementActionCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[CounterWidget.countKey]
            if(currentCount != null && currentCount <11) {
                prefs[CounterWidget.countKey] = currentCount + 1
            } else {
                prefs[CounterWidget.countKey] = 1
            }
        }
        CounterWidget.update(context, glanceId)
    }
}