package com.example.todo.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.theme.ToDoTheme
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo.R
import com.example.todo.model.Screen
import com.example.todo.fragments.Screens
import com.example.todo.TaskViewModel
import com.example.todo.ui.theme.bluePrimary
import com.example.todo.utils.AddTaskBottomSheet
import com.example.todo.utils.AddToDoButton
import com.example.todo.utils.BottomNavBar
import com.example.todo.utils.TaskTopAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.SelectedDay
import com.example.todo.ThemeViewModel
import com.example.todo.ToDo.Companion.prefs

@AndroidEntryPoint
class HomeScreenActivity : ComponentActivity(){

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    bluePrimary.toArgb(),
                    bluePrimary.toArgb()
                ),
               navigationBarStyle = SystemBarStyle.auto(
                    MaterialTheme.colorScheme.secondary.toArgb(),
                    MaterialTheme.colorScheme.secondary.toArgb(),



                    /*  resources.getColor(R.color.nav_bar_color),
                     resources.getColor(R.color.nav_bar_color)

                     */


                )


            )

            val themeDark = prefs?.themeDark
            themeDark.let {
                if (it != null) {
                    themeViewModel.setTheme(it)
                }
            }

            ToDoTheme(darkTheme = themeViewModel.isDarkThemeEnabled.value) {



                AppScreens(themeViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreens(themeViewModel: ThemeViewModel,viewModel: TaskViewModel = viewModel()) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val selectedDayhere = remember {

        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier
            .padding(top = TopAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding(),
                bottom = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {

            TaskTopAppBar(
                route = currentDestination?.route?:"",
                filterWithDate = {
                    selectedDayhere.value = it
                })
        },
        bottomBar = {

            if (navController.currentDestination?.route == Screen.EditTask.route){

            }else{
                BottomNavBar(navController)
            }

        }, floatingActionButton ={

            if (currentDestination?.route == Screen.ListOfToDos.route) {

                AddToDoButton {

                    showBottomSheet = true

                }
            }
        }

    ) {paddingValues->


       Screens(themeViewModel,paddingValues,navController,selectedDayhere)



        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {

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




