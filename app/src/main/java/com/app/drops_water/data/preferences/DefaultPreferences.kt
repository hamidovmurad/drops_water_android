package com.app.drops_water.data.preferences

import android.content.SharedPreferences
import java.time.Instant

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {

    override fun getGoal(): Int = sharedPref.getInt(Preferences.KEY_GOAL,0)

    override fun getCurrent(): Int = sharedPref.getInt(Preferences.KEY_CURRENT,0)

    override fun getDatetime(): Long = sharedPref.getLong(Preferences.KEY_DATETIME,-1)


    override fun setCurrent(current: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_CURRENT,current)
            .apply()
        sharedPref.edit()
            .putLong(Preferences.KEY_DATETIME, System.currentTimeMillis())
            .apply()
    }

    override fun setData(fullname: String, goal: Int) {
        sharedPref.edit()
            .putString(Preferences.KEY_FULLNAME,fullname)
            .apply()

        sharedPref.edit()
            .putInt(Preferences.KEY_GOAL,goal)
            .apply()
    }

    override fun getData(): Triple<String,Int,Int> {
        val fullname = sharedPref.getString(Preferences.KEY_FULLNAME,"")?:""
        val goal = sharedPref.getInt(Preferences.KEY_GOAL,0)
        val current = sharedPref.getInt(Preferences.KEY_CURRENT,0)

        return Triple(
            fullname,
            goal,
            current
        )
    }


    override fun isSetUp(): Boolean = sharedPref.getInt(Preferences.KEY_GOAL,0)>0
}