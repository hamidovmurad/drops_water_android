package com.app.drops_water.data.preferences


interface Preferences {

    fun getGoal():Int

    fun getCurrent():Int

    fun getDatetime():Long

    fun setCurrent(current:Int)

    fun setData(fullname:String,goal:Int)

    fun getData(): Triple<String,Int,Int>

    fun isSetUp():Boolean


    companion object{
        const val PREF_NAME = "shared_pref"
        const val KEY_FULLNAME = "fullname"
        const val KEY_GOAL = "goal"
        const val KEY_CURRENT = "current"
        const val KEY_DATETIME = "datetime"
    }
}