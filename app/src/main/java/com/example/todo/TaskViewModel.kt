package com.example.todo

import android.content.Context
import  android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.dataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.SelectedDay
import com.example.domain.contract.DatabaseRepository
import com.example.todo.model.database.Task
import com.example.todo.model.database.TasksDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: DatabaseRepository) :ViewModel() {

    val selectedDay = mutableStateOf<String?>(SelectedDay.selectedDay)

    val selectedDayList = mutableStateListOf<Task>()



    // input a new task
    val inputNewTaskName = mutableStateOf<String?>("")

    val inputNewTaskDesc = mutableStateOf<String?>("")

    val inputNewTaskNameError = mutableStateOf<String>("")

    val inputNewTaskDescError = mutableStateOf<String>("")

    val inputSelectedDate = mutableStateOf<String>("")

    // selecting a task

    val selectedTask = mutableStateOf<Task?>(null)


    // edit task

    val editTaskName = mutableStateOf<String?>(null)

    val editTaskDesc = mutableStateOf<String?>(null)

    val editTaskIsDone = mutableStateOf<Boolean?>(null)

    val editTaskDate = mutableStateOf<String?>(null)


    val editTaskNameError = mutableStateOf<String>("")

    val editTaskDescError = mutableStateOf<String>("")

    // calendar

    var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    val mCalendar = Calendar.getInstance()

    var isDateSet = false


    var selectedMenuItem = mutableStateOf<String?>(null)


    fun textFieldValidation():Boolean{

        inputNewTaskNameError.value = ""

        inputNewTaskDescError.value = ""

        if (inputNewTaskName.value?.length!! <2){

            inputNewTaskNameError.value = "name is too short"
            return false
        }

        if (inputNewTaskName.value!!.isEmpty()){

            inputNewTaskNameError.value = "name is empty"
            return false
        }

        if (inputNewTaskDesc.value!!.length <2){

            inputNewTaskDescError.value = "description is too short"
            return false
        }

        if (inputNewTaskDesc.value!!.isEmpty()){

            inputNewTaskDescError.value = "description is empty"
            return false
        }

        return true

    }

    fun getTasks(){

        viewModelScope.launch() {

            val selectedDate = changeStringDateToLong(selectedDay.value!!)

            val listOfToDos = mutableListOf<Task>()

            repository.getAllTasks().collect{

                listOfToDos.addAll(it)
            }

            selectedDayList.clear()

            selectedDayList.addAll(listOfToDos.filter {

                    it.dateTime == selectedDate
            })
        }
    }

    fun editTask(id:Int){

        viewModelScope.launch() {

            selectedTask.value =  repository.getTaskById(id)

            editTaskDate.value = changeLongDateToString(selectedTask.value?.dateTime!!)
            editTaskName.value = selectedTask.value?.taskName
            editTaskDesc.value = selectedTask.value?.taskDesc
            editTaskIsDone.value = selectedTask.value?.isDone

        }
    }

    fun updateTask(){

        selectedTask.value?.taskName = editTaskName.value
        selectedTask.value?.taskDesc = editTaskDesc.value
        selectedTask.value?.isDone = editTaskIsDone.value!!
        selectedTask.value?.dateTime = changeStringDateToLong(editTaskDate.value!!)

        viewModelScope.launch() {

            selectedTask.value?.let { repository.updateTask(it) }

            getTasks()
        }



    }

    fun insertTask() {

        val date = changeStringDateToLong(inputSelectedDate.value)

        viewModelScope.launch() {

            repository.insertTask(Task(
                taskName = inputNewTaskName.value,
                taskDesc = inputNewTaskDesc.value,
                dateTime = date))
           getTasks()


        }

        inputNewTaskName.value = ""
        inputNewTaskDesc.value = ""
    }

    fun deleteTask(indexTask:Int){

        viewModelScope.launch() {

            repository.deleteTask(selectedDayList[indexTask])

        }

        selectedDayList.remove(selectedDayList[indexTask])

    }

    fun taskCompleted(task: Task){

        viewModelScope.launch {

            repository.updateTask(Task(
                id = task.id,
                taskName = task.taskName,
                taskDesc = task.taskDesc,
                dateTime = task.dateTime,
                isDone = true
            ))

            getTasks()
        }

    }


    fun changeLongDateToString(date:Long):String{

        val dateString = SimpleDateFormat("dd/MM/yyyy").format(date)

        return dateString
    }

    fun changeStringDateToLong(date:String):Long?{

        val dateLong = SimpleDateFormat("dd/MM/yyyy").parse(date)?.time

        return dateLong
    }

    fun setCalendar(){

        mCalendar.time = Date()

        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    }

    fun setCalendarToSelectedDate(){

        mYear =  editTaskDate.value!!.substring(6,10).toInt()
        mMonth = editTaskDate.value!!.substring(3,5).toInt() - 1
        mDay = editTaskDate.value!!.substring(0,2).toInt()

        isDateSet = true
    }

    fun getTodayDate(){

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        val todayDate = calendar.timeInMillis

         selectedDay.value = changeLongDateToString(todayDate)


    }

    fun readDarkModeState(context:Context){

       viewModelScope.launch {

           val isDark = ToDoDataStore(context).readDarkMode()

           if (isDark == true){

               selectedMenuItem.value = "Dark"
           } else {

               selectedMenuItem.value = "Light"
           }
       }
    }

    fun changeModeState(context:Context,value:Boolean){

        viewModelScope.launch {

            ToDoDataStore(context).changeDark(value)

            if (value == true){

                selectedMenuItem.value = "Dark"
            } else {

                selectedMenuItem.value = "Light"
            }

            Log.e("dark", ToDoDataStore(context).readDarkMode().toString())
        }
    }


}