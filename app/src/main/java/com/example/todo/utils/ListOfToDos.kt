package com.example.todo.utils

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.example.todo.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.SelectedDay

@Composable
fun ListOfToDos(selectedDayhere: MutableState<String>, viewModel: TaskViewModel = viewModel(), navigateToEditTask:(itemIndex:Int)->Unit) {


    if (!selectedDayhere.value.isNullOrEmpty()){

        viewModel.selectedDay.value = selectedDayhere.value
    }
    viewModel.getTasks()

        Column(

        ) {

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize(1f)
            ) {

                items(viewModel.selectedDayList.size) {

                        itemIndex ->
                    SwipeToDeleteContainer(
                        item = itemIndex,
                        onDelete = {

                            viewModel.deleteTask(itemIndex)
                        }

                    ) {
                        ToDoItem(viewModel, itemIndex, navigateToEditTask)
                    }


                }
            }

        }

}
