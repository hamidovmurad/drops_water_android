package com.app.drops_water.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.drops_water.R
import com.app.drops_water.presentation.ActionButton
import com.app.drops_water.presentation.SubTitle
import com.app.drops_water.presentation.TextFieldApp
import com.app.drops_water.presentation.TopAppBarApp
import com.app.drops_water.presentation.TrackerViewModel


@Composable
fun SettingsScreen(
    viewModel: TrackerViewModel,
    onBackPress :()->Unit){

    val errorMessage = stringResource(R.string.the_goal_must_be_greater_than_0)

    val info by viewModel.data.collectAsState()

    var textName by rememberSaveable { mutableStateOf(info.first) }
    var textGoal by rememberSaveable { mutableStateOf(info.second.toString()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBarApp(
            title = stringResource(R.string.edit_your_data),
            onBackClick = {
                onBackPress()
            })

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {


            SubTitle( stringResource(id = R.string.greeting_description3))


            Spacer(modifier = Modifier.height(32.dp))

            TextFieldApp(textName,hint =  stringResource(id = R.string.full_name)) { value ->
                textName = value
            }

            Spacer(modifier = Modifier.height(16.dp))


            TextFieldApp(
                textGoal,hint =  stringResource(id = R.string.daily_goal_by_glass),
                maxChar = 2,
                keyboardType = KeyboardType.Number
                ) { value ->
                textGoal = value
            }

        }

        ActionButton(title = stringResource(R.string.save),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        ) {
            if (textGoal.isNotBlank() && textGoal.toInt()>0) {
                viewModel.setInfo(textName,textGoal.toInt()).runCatching {
                    onBackPress()
                }

            }else{
                viewModel.showToast(errorMessage)
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}
