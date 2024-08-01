package com.example.todo.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.model.Screen
import com.example.todo.ui.theme.bluePrimary

@Composable
fun TaskTopAppBar(route:String,filterWithDate:(selectedDate:String)->Unit, navigateToSettings:()->Unit){

    val dropDownMenuExpanded = remember {

        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight()
            .background(bluePrimary)
            .padding(top = 20.dp)
    ) {

        if (route == Screen.ListOfToDos.route){

            Row (Modifier.fillMaxWidth(1f)) {

                Text(
                    text = "To Do List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Box (modifier = Modifier.wrapContentSize(Alignment.TopEnd)){

                    Image(
                        painter = painterResource(id = R.drawable.baseline_more_vert_24),
                        contentDescription = "settings unselected",
                        modifier = Modifier.padding(end = 20.dp)
                            .clickable {
                                dropDownMenuExpanded.value = true
                            }
                    )

                    DropdownMenu(
                        expanded = dropDownMenuExpanded.value,
                        onDismissRequest = {

                            dropDownMenuExpanded.value = false
                        }) {

                        DropdownMenuItem(text = {

                            Text(text = "Settings")

                        }, onClick = {

                            dropDownMenuExpanded.value = false

                            navigateToSettings()
                        })

                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            TopCalendar(filterWithDate)

        } else if (route == Screen.Settings.route){

            Row (Modifier.fillMaxWidth(1f)) {

                Text(
                    text = "Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )



            }

            Spacer(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(1f)
                    .background(bluePrimary)
            )

        }

    }

}
