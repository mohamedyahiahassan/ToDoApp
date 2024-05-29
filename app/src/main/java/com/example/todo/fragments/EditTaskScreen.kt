package com.example.todo.fragments

import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.theme.bluePrimary
import com.example.todo.utils.TaskTextField
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.SelectedDay
import com.example.todo.TaskViewModel


@Composable
fun EditTask(uniqueId:Int, viewModel: TaskViewModel = viewModel(), saveEditedTask:()->Unit){


    LaunchedEffect(key1 = Unit){

        viewModel.editTask(uniqueId)

        viewModel.setCalendar()

    }
    val mContext = LocalContext.current

    if (!viewModel.editTaskDate.value.isNullOrEmpty()&&viewModel.isDateSet == false){

        viewModel.setCalendarToSelectedDate()
    }




    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->

            viewModel.editTaskDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"

        }, viewModel.mYear, viewModel.mMonth, viewModel.mDay
    )


    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box {

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .wrapContentHeight()
                    .background(bluePrimary)
            ) {

                androidx.compose.material3.Text(
                    text = "To Do List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(1f)
                        .background(bluePrimary)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 80.dp)
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f)
                    .background(
                        colorResource(id = R.color.to_do_item_color),
                        RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .align(alignment = Alignment.Center)
            ) {

                Text(
                    text = "Edit Task",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.text_color))

                Spacer(modifier = Modifier.height(20.dp))

                TaskTextField(
                    textFieldInput = viewModel.editTaskName,
                    placeHolderText = "",
                    viewModel.editTaskNameError)

                Spacer(modifier = Modifier.height(20.dp))

                TaskTextField(
                    textFieldInput = viewModel.editTaskDesc,
                    placeHolderText = "",
                    viewModel.editTaskDescError)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Select time",
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.text_color))

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = viewModel.editTaskDate.value.toString(),
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.clickable {

                        mDatePickerDialog.show()

                    })

                Spacer(modifier = Modifier.height(20.dp))

                Button(

                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = {

                        saveEditedTask()

                    },

                ) {

                    androidx.compose.material3.Text(text = "save", color = Color.White)



                }



            }
        }

    }






}
