package com.example.todo.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SelectedDay
import com.example.todo.ui.theme.backgroundColor
import com.example.todo.ui.theme.bluePrimary
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TopCalendar(filterWithDate:(selectedDate:String)->Unit){

    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) } // Adjust as needed
    val endDate = remember { currentDate.plusDays(500) } // Adjust as needed
    var selection by remember { mutableStateOf(currentDate) }

    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY) }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = daysOfWeek.first()
    )

    WeekCalendar(
        modifier = Modifier.background(color = bluePrimary),
        state = state,
        dayContent = { day ->
            Day(day.date, isSelected = selection == day.date) { clicked ->

                if (selection != clicked) {
                    selection = clicked
                }

                val dateToSend = SelectedDay.changeCalendarToRequiredFormat(clicked.toString())

                filterWithDate(dateToSend)

            }
        },
    )
}
@Composable
fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {

    val dateFormatter = DateTimeFormatter.ofPattern("dd")

    Box(
        modifier = Modifier
            .fillMaxWidth()
          //  .wrapContentHeight()
            .height(70.dp)
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.toString().substring(0,3),
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = if (isSelected) MaterialTheme.colorScheme.background else Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}