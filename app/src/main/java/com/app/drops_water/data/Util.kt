package com.app.drops_water.data

import java.text.SimpleDateFormat
import java.util.Date


fun Long.longToStringDate():String{
  return  try {
        if (this>-1) {
            val sdf = SimpleDateFormat("E, dd MMM yyyy, hh:mm a")
            val netDate = Date(this)
            sdf.format(netDate)
        } else "Not Found!"
    } catch (e: Exception) {
         "Not Found!"
    }
}