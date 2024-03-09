package com.app.drops_water.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.drops_water.R
import com.app.drops_water.navigations.Route
import com.app.drops_water.navigations.UiEvent
import com.app.drops_water.presentation.ActionButton
import com.app.drops_water.presentation.SubTitle
import com.app.drops_water.presentation.TopAppBarApp
import com.app.drops_water.ui.theme.Black
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.GrayDark
import com.app.drops_water.ui.theme.Green
import com.app.drops_water.ui.theme.Red
import com.app.drops_water.ui.theme.White


@Composable
fun MainScreen(onNavigate:(UiEvent.Navigate)->Unit){

    var currentNumber by rememberSaveable { mutableStateOf(0) }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        TopAppBarApp(
            "☀ ☼ ☽ ☁ Good Morning,",
            "Adam Smith",
            onActionAnalysis = {
                onNavigate(UiEvent.Navigate(Route.ANALYSIS))
            },
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


            Text(text = "Today - 11:00 am",
                color = Black,
                fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                fontSize = 20.sp
            )
            Text(text = "Last drinking time",
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
                Text(text = "Edit goal",
                    color = Black,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    fontSize = 12.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Your Daily Goal (%)",
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

                        if (it == currentNumber-1) {
                            Text(
                                text = "$currentNumber glass -->",
                                color = Black,
                                fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text = "${(it + 1) * 10} ―――",
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


                if(currentNumber>0) {
                    val img = when (currentNumber) {
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
            title = "Add +1 ($currentNumber/10)",
            color = if(currentNumber<10)Blue else if(currentNumber<20) Green else Red,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        ) {
            currentNumber++
        }

        Spacer(modifier = Modifier
            .height(16.dp))
        
        SubTitle(subtitle = "You got 60% of today’s goal, keep focus on your health!",
           modifier = Modifier.padding(start = 24.dp, end = 24.dp) )

        Spacer(modifier = Modifier
            .height(24.dp))

    }

}


@Preview(showBackground = true)
@Composable
fun Prev(){
    MainScreen{}

}