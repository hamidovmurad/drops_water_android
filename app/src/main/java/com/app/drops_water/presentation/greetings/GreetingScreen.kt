package com.app.drops_water.presentation.greetings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.drops_water.R
import com.app.drops_water.navigations.Route
import com.app.drops_water.navigations.UiEvent
import com.app.drops_water.presentation.ActionButton
import com.app.drops_water.presentation.TrackerViewModel
import com.app.drops_water.ui.theme.Blue
import com.app.drops_water.ui.theme.Grey
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreetingScreen(
    viewModel: TrackerViewModel,
    onNavigate :(UiEvent.Navigate)->Unit
){


    val errorMessage = stringResource(R.string.the_goal_must_be_greater_than_0)

    val greetings = listOf(
        Triple(R.drawable.img_greeting_1,R.string.greeting_title1,R.string.greeting_description1),
        Triple(R.drawable.img_greeting_2,R.string.greeting_title2,R.string.greeting_description2),
        Triple(R.drawable.img_greeting_3,R.string.greeting_title3,R.string.greeting_description3),
        Triple(-1,R.string.login_title,R.string.login_description)
    )

    var textName by rememberSaveable { mutableStateOf("") }
    var textGoal by rememberSaveable { mutableStateOf("0") }

    var buttonName by rememberSaveable { mutableStateOf(R.string.next) }

    val pagerState = rememberPagerState{greetings.size}
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            buttonName = if (page < greetings.size-1) R.string.next
            else R.string.get_started
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
            if (it == greetings.size-1) {
                AddInfo(greetings[it],
                    onNameChanged = { name ->
                        textName = name
                    },
                    onGoalChanged = { goal ->
                        textGoal = goal
                    })
            }else ItemGreeting(greetings[it])
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
            title = stringResource(id = buttonName),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {


            if (pagerState.currentPage == 0 || pagerState.currentPage<3) {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }else {
                if (textGoal.isNotBlank() && textGoal.toInt()>0) {
                    viewModel.setTimer()
                    viewModel.setData(textName, textGoal.toInt()).runCatching {
                        onNavigate(UiEvent.Navigate(Route.MAIN))
                    }
                }else{
                    viewModel.showToast(errorMessage)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}