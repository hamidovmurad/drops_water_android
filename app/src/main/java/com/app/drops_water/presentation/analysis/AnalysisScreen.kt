package com.app.drops_water.presentation.analysis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.drops_water.R
import com.app.drops_water.navigations.UiEvent
import com.app.drops_water.presentation.SubTitle
import com.app.drops_water.presentation.TopAppBarApp
import com.app.drops_water.ui.theme.Black
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.BlueLight
import com.app.drops_water.ui.theme.BlueLight2
import com.app.drops_water.ui.theme.Green
import com.app.drops_water.ui.theme.Red
import com.app.drops_water.ui.theme.White

@Composable
fun AnalysisScreen(onBackPress :()->Unit){


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBarApp(
            title = "Analysis",
            onBackClick = {
                onBackPress()
            })

        Spacer(modifier = Modifier.height(16.dp))

        SubTitle("Staying hydrated every day is easy with Drops Water Tracker.",
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))


        LazyColumn{

            items(8){

                val goal = 10
                val current = 5+it
                when(it){
                    0-> {
                        Text(
                            text = "Today",
                            color = Black,
                            fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 24.dp)

                        )

                        Spacer(modifier = Modifier.height(8.dp))


                        ItemProgress(it,8,4)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Previews 7 days",
                            color = Black,
                            fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                    }
                    else -> ItemProgress(it-1,goal,current)
                }



            }

        }


    }
}


@Composable
fun ItemProgress(
    position:Int,
    goal:Int,
    current:Int
){
    val weekDays = listOf("Mo.","Tu.","We.","Th.","Fr.","Sa","Su.")

    Row(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "${position+25} Mar ${weekDays[position]}",
            color = Black,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,

            )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier.weight(1f)
        ) {

            for (i in 1..goal) {
                val roundStart = if (i == 1) 4 else 0
                val roundEnd = if (i == goal) 4 else 0
                val color = if (i <= current){ if (current>20 ) Red else if(current>=goal) Green else Blue }else BlueLight2
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = roundStart.dp,
                                bottomStart = roundStart.dp,
                                topEnd = roundEnd.dp,
                                bottomEnd = roundEnd.dp,
                            )
                        )
                        .background(color)
                )
                Spacer(modifier = Modifier.width(1.dp))

            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$current/$goal",
            color = Black,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )

    }
}


@Preview(showBackground = true)
@Composable
fun Prev(){
    AnalysisScreen{}
}