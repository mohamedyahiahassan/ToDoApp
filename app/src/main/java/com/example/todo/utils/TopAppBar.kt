package com.example.todo.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.model.Screen
import com.example.todo.ui.theme.bluePrimary

@Composable
fun TaskTopAppBar(route:String,filterWithDate:(selectedDate:String)->Unit){

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight()
            .background(bluePrimary)
            .padding(top = 20.dp)
    ) {

        if (route == Screen.ListOfToDos.route){
            Text(
                text = "To Do List",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp))

            Spacer(modifier = Modifier.height(10.dp))

            TopCalendar(filterWithDate)

        } else if (route == Screen.Settings.route){

            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp))

            Spacer(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(1f)
                    .background(bluePrimary))



        }

    }

}
