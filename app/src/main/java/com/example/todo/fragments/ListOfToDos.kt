package com.example.todo.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.utils.SwipeToDeleteContainer
import com.example.todo.utils.ToDoItem

@Composable
fun ListOfToDos(
    selectedDayhere: MutableState<String>,
    viewModel: TaskViewModel = viewModel(),
    navigateToEditTask:(itemIndex:Int)->Unit

) {


    LaunchedEffect(key1 = Unit) {

        viewModel.getTodayDate()

        viewModel.getTasks()

    }

    LaunchedEffect(key1 = selectedDayhere.value) {

        if (!selectedDayhere.value.isNullOrEmpty()){

            viewModel.selectedDay.value = selectedDayhere.value

            viewModel.getTasks()
        }


    }



    Column(

        ) {

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {

                itemsIndexed(
                    viewModel.selectedDayList, key = {itemIndex, item -> item.id!!}

                ) { itemIndex, item ->

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

        Spacer(modifier = Modifier.height(20.dp))

        }

}
