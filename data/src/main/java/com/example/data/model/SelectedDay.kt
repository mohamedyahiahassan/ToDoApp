package com.example.data.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


object SelectedDay {

    var selectedDay :String = ""

    var isDarkModeOn = false


    init {

       val todayDate = Calendar.getInstance().time

        val formatWanted = SimpleDateFormat("dd/MM/yyyy")

        selectedDay = formatWanted.format(todayDate)


    }

    fun changeCalendarToRequiredFormat(date:String):String{

        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd")

        val targetFormat =SimpleDateFormat("dd/MM/yyyy")

        val nonFinalDate = originalDateFormat.parse(date)

        val finalFormat = targetFormat.format(nonFinalDate)

        selectedDay = finalFormat

        return finalFormat
    }
}