package com.example.todo.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.theme.ToDoTheme
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo.R
import com.example.todo.model.Screen
import com.example.todo.TaskViewModel
import com.example.todo.ui.theme.bluePrimary
import com.example.todo.utils.AddTaskBottomSheet
import com.example.todo.utils.AddToDoButton
import com.example.todo.utils.BottomNavBar
import com.example.todo.utils.TaskTopAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo.ToDoDataStore
import com.example.todo.fragments.EditTask
import com.example.todo.fragments.SettingsTab
import com.example.todo.fragments.ListOfToDos
import com.example.todo.ui.theme.bluePrimary_dark
import com.example.todo.ui.theme.navbarColor_dark
import kotlinx.coroutines.coroutineScope

@AndroidEntryPoint
class HomeScreenActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        val context = applicationContext

        val darkMode = mutableStateOf<Boolean>(false)

        lifecycleScope.launch {

             darkMode.value = ToDoDataStore(context).readDarkMode()?:false
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(bluePrimary.toArgb()),
        )
        setContent {

            ToDoTheme(
                darkTheme = darkMode.value
            ) {

                AppScreens(){

                    darkMode.value = it
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreens(changeDarkMode:(value:Boolean)->Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val selectedDayhere = remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier
            .padding(top = TopAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding()),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TaskTopAppBar(
                route = currentDestination?.route?:"",
                filterWithDate = {
                    selectedDayhere.value = it
                },
                navigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        },
        floatingActionButton ={

            if (currentDestination?.route == Screen.ListOfToDos.route) {

                AddToDoButton {

                    showBottomSheet = true

                }
            }
        }

    ) {paddingValues->


        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {


            NavHost(
                navController = navController,
                startDestination = Screen.ListOfToDos.route
            ) {

                composable(Screen.ListOfToDos.route) {

                    val viewModel = hiltViewModel<TaskViewModel>()
                    ListOfToDos(selectedDayhere,viewModel,
                        navigateToEditTask = {uniqueId->
                            navController.navigate("${Screen.EditTask.route}/${uniqueId}")
                        })
                }

                composable(Screen.Settings.route) {

                    val viewModel = hiltViewModel<TaskViewModel>()
                    SettingsTab(viewModel){

                        changeDarkMode(it)
                    }
                }

                composable("${Screen.EditTask.route}/{uniqueId}",
                    arguments = listOf(navArgument("uniqueId"){

                        type = NavType.IntType
                    })){

                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(Screen.ListOfToDos.route)
                    }

                    val viewModel = hiltViewModel<TaskViewModel>(parentEntry)

                    val uniqueId = it.arguments?.getInt("uniqueId")

                    if (uniqueId != null) {
                        EditTask(uniqueId,viewModel = viewModel){

                            viewModel.updateTask()

                            navController.navigateUp()

                        }
                    }
                }

            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {


                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(Screen.ListOfToDos.route)
                }

                val viewModel = hiltViewModel<TaskViewModel>(parentEntry)

                AddTaskBottomSheet(viewModel){

                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }

                }
            }
        }

    }
}




