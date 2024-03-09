package com.app.drops_water.data.preferences

import android.content.SharedPreferences

class DefaultPreferences(
    private val sharedPref :SharedPreferences
) : Preferences {


    override fun saveFullname(fullname: String) {
        sharedPref.edit()
            .putString(Preferences.KEY_FULLNAME,fullname)
            .apply()
    }

    override fun saveGoal(goal: String) {
        sharedPref.edit()
            .putString(Preferences.KEY_GOAL,goal)
            .apply()
    }


    override fun loadInfo(): Pair<String?,String?> {
        val fullaame = sharedPref.getString(Preferences.KEY_FULLNAME,null)
        val goal = sharedPref.getString(Preferences.KEY_GOAL,null)

        return Pair(
            fullaame,
            goal
        )
    }
}