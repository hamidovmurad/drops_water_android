package com.app.drops_water.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
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
import com.app.drops_water.presentation.DisplayWaterImage
import com.app.drops_water.ui.theme.Black
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.White

@Composable
fun Context.WidgetContent(
    count :Int,
    countPercent :Int,
){
    Column(
        modifier = GlanceModifier
            .fillMaxSize().padding(4.dp)
            .background(White),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {


        Button(
            text = getString(R.string.add_1),
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


            if (countPercent > 0) {
                val img = DisplayWaterImage(countPercent)
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

@Composable
fun Context.WidgetEmptyContent(){
    Column(
        modifier = GlanceModifier
            .fillMaxSize().padding(16.dp)
            .background(Blue),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(
            text = getString(R.string.goal_not_set),
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

