package com.app.drops_water.presentation.greetings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.drops_water.R
import com.app.drops_water.presentation.TextFieldApp
import com.app.drops_water.ui.theme.Black

@Composable
fun AddInfo(
    item:Triple<Int,Int,Int>,
    onNameChanged:(name:String)->Unit,
    onGoalChanged:(goal:String)->Unit
){
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {

        Text(
            text = stringResource(id = item.second),
            fontFamily = FontFamily(Font(R.font.inter_extra_bold)),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Black

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = item.third),
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            fontSize = 16.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextFieldApp(hint = stringResource(R.string.full_name)){ value->
            onNameChanged(value)
        }

        Spacer(modifier = Modifier.height(16.dp))


        TextFieldApp(
            hint = stringResource(R.string.daily_goal_by_glass),
            keyboardType = KeyboardType.Number
        ){ value->
            onGoalChanged(value)
        }


    }

}


@Composable
fun ItemGreeting(item:Triple<Int,Int,Int>){

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = item.first),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
                .width(240.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = stringResource(id = item.second),
            fontFamily = FontFamily(Font(R.font.inter_extra_bold)),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Black

        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = item.third),
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Black
        )


    }

}