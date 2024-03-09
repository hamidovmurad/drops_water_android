package com.app.drops_water.presentation.greetings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.drops_water.R
import com.app.drops_water.navigations.Route
import com.app.drops_water.presentation.ActionButton
import com.app.drops_water.navigations.UiEvent
import com.app.drops_water.presentation.TextFieldApp
import com.app.drops_water.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreetingScreen(
    onNavigate :(UiEvent.Navigate)->Unit
){

    val greetings = listOf(
        Triple(R.drawable.img_greeting_1,R.string.greeting_title1,R.string.greeting_description1),
        Triple(R.drawable.img_greeting_2,R.string.greeting_title2,R.string.greeting_description2),
        Triple(R.drawable.img_greeting_3,R.string.greeting_title3,R.string.greeting_description3),
        Triple(-1,R.string.login_title,R.string.login_description)

    )

    var buttonName by rememberSaveable { mutableStateOf("Next") }
    var visibleSkip by remember { mutableStateOf(true) }

    val pagerState = rememberPagerState{4}
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
           if (page<3) {
               visibleSkip = true
               buttonName = "Next"
           }else{
               visibleSkip = false
               buttonName = "Get Started"
           }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp)
    ) {

        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            key = {greetings[it]},
            pageSize = PageSize.Fill
        ) {
            if (it==3)AddInfo(greetings[it])
            else ItemGreeting(greetings[it])
        }

        Spacer(modifier = Modifier.height(32.dp))

        DotsIndicator(
            totalDots = greetings.size,
            selectedIndex = pagerState.currentPage,
            selectedColor = Blue,
            unSelectedColor = Grey
        )

        Spacer(modifier = Modifier.height(64.dp))


        ActionButton(
            title = buttonName,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
            if (pagerState.currentPage ==0 || pagerState.currentPage<3) {
                scope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage + 1
                    )
                }
            }else {
                onNavigate(UiEvent.Navigate(Route.MAIN))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



    }
}


@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(24.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(24.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}



@Composable
fun AddInfo(item:Triple<Int,Int,Int>){

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

        var textName by rememberSaveable { mutableStateOf("") }
        TextFieldApp("Full name"){ value->
            textName = value
        }

        Spacer(modifier = Modifier.height(16.dp))


        var textEmail by rememberSaveable { mutableStateOf("") }
        TextFieldApp("Daily goal (by glass 250ml)"){ value->
            textEmail = value
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

@Preview(showBackground = true)
@Composable
fun Prev(){
    GreetingScreen(){}
}