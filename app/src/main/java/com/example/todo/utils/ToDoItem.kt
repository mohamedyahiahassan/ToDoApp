package com.example.todo.utils

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.TaskViewModel
import com.example.todo.ui.theme.bluePrimary
import com.example.todo.ui.theme.green
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun ToDoItem(viewModel: TaskViewModel = viewModel(), itemIndex:Int, navigateToEditTask:(itemIndex:Int)->Unit) {


    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth(1f)
            .fillMaxHeight(0.15f)
            .background(
               // colorResource(id = R.color.to_do_item_color),
                MaterialTheme.colorScheme.secondary,
                RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable {

                viewModel.selectedTask.value = viewModel.selectedDayList[itemIndex]

                navigateToEditTask(viewModel.selectedTask.value!!.id!!)
            }
    ){


        Divider(
            color = if (viewModel.selectedDayList[itemIndex].isDone==false) bluePrimary else green,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .height(70.dp)
                .width(3.dp)
        )



        Column(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .fillMaxWidth(0.7f)
        ) {

            Text(
                text = viewModel.selectedDayList[itemIndex].taskName?:"",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                color =  if (viewModel.selectedDayList[itemIndex].isDone==false) bluePrimary else green)

            Spacer(modifier = Modifier.weight(1f))

            Text(text = viewModel.selectedDayList[itemIndex].taskDesc?:"",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp)
        }

        if ( viewModel.selectedDayList[itemIndex].isDone == false){

            Button(
                modifier = Modifier.width(80.dp),
                onClick = {
                          viewModel.taskCompleted(viewModel.selectedDayList[itemIndex])

                },
                shape = RoundedCornerShape(10.dp),
                colors =  if (viewModel.selectedDayList[itemIndex].isDone==false) ButtonDefaults.buttonColors(bluePrimary)
                else ButtonDefaults.buttonColors(Color.White),
            ) {


                Image(
                    painter = painterResource(id = R.drawable.check_icon),
                    contentDescription = "check icon",
                )
            }
        }else{

                Text(
                    modifier = Modifier.width(80.dp),
                    text = " Done !",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    color =  green,
                   )

                }

    }

}