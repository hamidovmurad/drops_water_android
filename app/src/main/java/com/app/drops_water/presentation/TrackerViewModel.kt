package com.app.drops_water.presentation

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.drops_water.data.DateChangedBroadcastReceiver
import com.app.drops_water.data.longToStringDate
import com.app.drops_water.data.preferences.Preferences
import com.app.drops_water.data.scheduleAlarm
import com.app.drops_water.widget.TrackerWidget
import com.app.drops_water.widget.TrackerWidget.KEY_WIDGET_COUNT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar


class TrackerViewModel(
    private val application: Application,
    private val preferences: Preferences
): ViewModel(){


    private val _data = MutableStateFlow(Triple("",0,0))
    val data = _data.asStateFlow()

    private val _datetime = MutableStateFlow("")
    val datetime = _datetime.asStateFlow()


    fun isSetUp():Boolean = preferences.isSetUp()

    fun setCount(count:Int)= viewModelScope.launch {
        GlanceAppWidgetManager(application)
            .getGlanceIds(TrackerWidget::class.java).firstOrNull()?.let { glanceId->
                updateAppWidgetState(application, glanceId) { prefs ->
                    prefs[intPreferencesKey(KEY_WIDGET_COUNT)] = count
                }
                TrackerWidget.update(application, glanceId)
            }
        preferences.setCount(count)
    }

    fun setData(
        fullName :String,
        goal:Int,
    ) = viewModelScope.launch {
        preferences.setData(fullName, goal)
        GlanceAppWidgetManager(application)
            .getGlanceIds(TrackerWidget::class.java).firstOrNull()?.let { glanceId->
                TrackerWidget.update(application, glanceId)
            }
    }


    fun getData() = viewModelScope.launch {
        val data = preferences.getData()
        _data.emit(data)

        _datetime.emit(preferences.getDatetime().longToStringDate())

    }



    fun showToast(message:String){
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
    }


    fun setTimer() = viewModelScope.launch {
        application.scheduleAlarm()
    }





}