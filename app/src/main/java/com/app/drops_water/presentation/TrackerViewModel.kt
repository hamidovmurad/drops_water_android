package com.app.drops_water.presentation

import android.app.Application
import android.widget.Toast
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.drops_water.data.preferences.Preferences
import com.app.drops_water.widget.TrackerWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class TrackerViewModel(
    private val application: Application,
    private val preferences: Preferences
): ViewModel(){


    private val _data = MutableStateFlow<Triple<String,Int,Int>>(Triple("",0,0))
    val data = _data.asStateFlow()

    private val _datetime = MutableStateFlow<String>("")
    val datetime = _datetime.asStateFlow()



    fun isSetUp():Boolean = preferences.isSetUp()

    fun setCurrent( current:Int)= viewModelScope.launch {
        GlanceAppWidgetManager(application)
            .getGlanceIds(TrackerWidget::class.java).firstOrNull()?.let { glanceId->
                updateAppWidgetState(application, glanceId) { prefs ->
                    prefs[intPreferencesKey("count")] = current
                }
                TrackerWidget.update(application, glanceId)
            }
        preferences.setCurrent(current)
    }

    fun setInfo(
        fullname :String,
        goal:Int
    ) = viewModelScope.launch {
        preferences.setData(fullname, goal)
        GlanceAppWidgetManager(application)
            .getGlanceIds(TrackerWidget::class.java).firstOrNull()?.let { glanceId->
                TrackerWidget.update(application, glanceId)
            }
    }


    fun getData() = viewModelScope.launch {
        val data = preferences.getData()
        _data.emit(data)

        if (preferences.getDatetime()!=-1L){
            val sdf = SimpleDateFormat("E, dd MMM yyyy, hh:mm a")
            val netDate = Date(preferences.getDatetime())
            _datetime.emit(sdf.format(netDate))
        }else   _datetime.emit("Not Found!")

    }



    fun showToast(message:String){
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
    }




}