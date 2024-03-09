package com.app.drops_water.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.drops_water.presentation.ActionButton
import com.app.drops_water.presentation.SubTitle
import com.app.drops_water.presentation.TextFieldApp
import com.app.drops_water.presentation.TopAppBarApp


@Composable
fun SettingsScreen( onBackPress :()->Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBarApp(
            title = "Edit your data",
            onBackClick = {
                onBackPress()
            })

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {


            SubTitle("Staying hydrated every day is easy with Drops Water Tracker.")


            Spacer(modifier = Modifier.height(32.dp))

            var textName by rememberSaveable { mutableStateOf("") }
            TextFieldApp("Full name") { value ->
                textName = value
            }

            Spacer(modifier = Modifier.height(16.dp))


            var textEmail by rememberSaveable { mutableStateOf("") }
            TextFieldApp("Daily goal (by glass 250ml)") { value ->
                textEmail = value
            }



        }

        ActionButton(title = "Save",
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        ) {
            onBackPress()
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}


@Preview(showBackground = true)
@Composable
fun Prev(){
    SettingsScreen { }
}