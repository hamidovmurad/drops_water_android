package com.app.drops_water.navigations

sealed class UiEvent {
    data class Navigate(val route:String):UiEvent()
    object NavigateUp:UiEvent()

}