package com.app.drops_water.data.preferences


interface Preferences {

    fun saveFullname(fullname:String)
    fun saveGoal(goal:String)
    fun loadInfo(): Pair<String?,String?>

    companion object{
        const val KEY_FULLNAME = "fullname"
        const val KEY_GOAL = "goal"
    }
}