package com.example.todo.utils

import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    viewModel: TaskViewModel,
    saveCreatedTask:()->Unit
){


    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    LaunchedEffect(key1 = Unit){
        viewModel.inputSelectedDate.value = "$mDay/${mMonth + 1}/$mYear"
    }

    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->

            viewModel.inputSelectedDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"

        }, mYear, mMonth, mDay
    )

    Column (modifier = Modifier
        .fillMaxWidth(1f)
        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ){
        Text(
            text = "Add new Task",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TaskTextField(
            textFieldInput = viewModel.inputNewTaskName,
            placeHolderText = "enter your task",
            errorMessage = viewModel.inputNewTaskNameError)

        Spacer(modifier = Modifier.height(20.dp))

        TaskTextField(
            textFieldInput = viewModel.inputNewTaskDesc,
            placeHolderText = "enter the description",
            errorMessage = viewModel.inputNewTaskDescError)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Select time",
            fontSize = 18.sp,
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text =  viewModel.inputSelectedDate.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {

                    mDatePickerDialog.show()
                })

        Spacer(modifier = Modifier.height(20.dp))
        
        Button(
            onClick = {

               if (viewModel.textFieldValidation()) {

                   viewModel.insertTask()

                   saveCreatedTask()
               }

                      },
            modifier = Modifier.align(Alignment.End)

        ) {
            
            Text(text = "save", color = Color.White)



        }

        Spacer(modifier = Modifier.height(100.dp))

    }
}