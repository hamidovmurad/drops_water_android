package com.app.drops_water.navigations

import androidx.navigation.NavController

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}