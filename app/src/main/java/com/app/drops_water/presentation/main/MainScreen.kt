package com.app.drops_water.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.drops_water.R
import com.app.drops_water.navigations.Route
import com.app.drops_water.navigations.UiEvent
import com.app.drops_water.presentation.ActionButton
import com.app.drops_water.presentation.DisplayWaterImage
import com.app.drops_water.presentation.SubTitle
import com.app.drops_water.presentation.TopAppBarApp
import com.app.drops_water.presentation.TrackerViewModel
import com.app.drops_water.ui.theme.Black
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.GrayDark
import com.app.drops_water.ui.theme.Green
import com.app.drops_water.ui.theme.Red
import com.app.drops_water.ui.theme.White


@Composable
fun MainScreen(
    viewModel: TrackerViewModel,
    onNavigate:(UiEvent.Navigate)->Unit){

    viewModel.getData()
    val data by viewModel.data.collectAsState()

    val goal = data.second
    var count by rememberSaveable { mutableStateOf(data.third) }
    val countPercent = count*10/goal


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        TopAppBarApp(
            stringResource(R.string.welcome_back),
            data.first,
            onActionSettings = {
                onNavigate(UiEvent.Navigate(Route.SETTINGS))
            })

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 24.dp)
                .paint(
                    painter = painterResource(R.drawable.ic_main_box),
                    contentScale = ContentScale.FillBounds
                )
                .padding(16.dp)
        ){

            Text(text = viewModel.datetime.collectAsState().value,
                color = Black,
                fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                fontSize = 16.sp
            )
            Text(text = stringResource(R.string.last_drinking_time),
                color = GrayDark,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                colors = ButtonDefaults.textButtonColors(White),
                onClick = {
                    onNavigate(UiEvent.Navigate(Route.SETTINGS))
                }) {
                Text(text = stringResource(R.string.edit_goal),
                    color = Black,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.your_daily_goal,count,goal),
            color = Black,
            fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ){


            LazyColumn(
                reverseLayout = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f)
            ){
                items(10){
                    Row(
                        horizontalArrangement = Arrangement.Absolute.Right,
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(24.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        if (it == (countPercent-1) || (it==0 && (countPercent-1) ==-1 && count>0)) {
                            Text(
                                text = stringResource(R.string.glass, count),
                                color = Black,
                                fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text = "${(it + 1) * 10}% ―――",
                            color = Black,
                            fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.width(12.dp))


            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.weight(1f)
            ){
                if(countPercent>0) {
                    val img = DisplayWaterImage(countPercent)
                    Image(
                        painter = painterResource(id = img),
                        contentDescription = "water",
                    )
                }
                Image(painter = painterResource(id = R.drawable.ic_glass),
                    contentDescription ="glass" )
            }


        }


        Spacer(modifier = Modifier.weight(1f))


        ActionButton(
            title = stringResource(id = R.string.add_1),
            color = if(count<goal)Blue else if(count<20) Green else Red,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        ) {
            count++
            viewModel.setCount(count)
        }

        Spacer(modifier = Modifier
            .height(16.dp))
        
        SubTitle(subtitle = stringResource(
            R.string.greeting_description3,
            goal
        ),
           modifier = Modifier.padding(start = 24.dp, end = 24.dp) )

        Spacer(modifier = Modifier
            .height(24.dp))

    }

}
