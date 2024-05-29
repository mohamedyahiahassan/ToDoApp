package com.example.todo.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo.model.Screen


@Composable
fun BottomNavBar(navController: NavHostController) {

    BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.secondary
            // colorResource(id = R.color.nav_bar_color)
        ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination



            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screen.ListOfToDos.route } == true,
                onClick = {
                    navController.navigate(Screen.ListOfToDos.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screen.ListOfToDos.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.list_icon_selected),
                            contentDescription = "list selected",
                            modifier = Modifier.size(30.dp)
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.list_icon_unselected),
                            contentDescription = "list unselected",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                })

            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true,
                onClick = {

                    navController.navigate(Screen.Settings.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.settings_icon_selected),
                            contentDescription = "settings selected",
                            modifier = Modifier.size(30.dp)
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.settings_icon_unselected),
                            contentDescription = "settings unselected",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                })
        }


    }