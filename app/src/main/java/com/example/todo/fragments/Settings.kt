package com.example.todo.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.TaskViewModel
import com.example.todo.ui.theme.bluePrimary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTab(viewModel: TaskViewModel,changeDarkMode:(value:Boolean)->Unit) {


    var isDropDownMenuExpanded by rememberSaveable {
        mutableStateOf<Boolean>(false)
    }

    val context = LocalContext.current



    LaunchedEffect(key1 = Unit) {

        viewModel.readDarkModeState(context)
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(1f)
    ) {



        Text(
            text = "Mode",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 40.dp, top = 20.dp)
        )

        ExposedDropdownMenuBox(
            expanded = isDropDownMenuExpanded,
            onExpandedChange = {
                               isDropDownMenuExpanded = it
                               },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter)
        ) {

            OutlinedTextField(
                value = viewModel.selectedMenuItem.value?:"Light",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .menuAnchor(),
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.dropdown_arrow),
                        contentDescription = "drop down arrow",
                        modifier = Modifier.clickable {

                            if (isDropDownMenuExpanded == false) isDropDownMenuExpanded = true
                        })


                },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = bluePrimary,
                    unfocusedTextColor = bluePrimary,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = bluePrimary,
                    unfocusedBorderColor = bluePrimary
                )

            )

            ExposedDropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(Color.White)
            ) {

                DropdownMenuItem(
                    text = {
                        Text(text = "Light", color = Color.Black)
                    },

                    onClick = {
                        isDropDownMenuExpanded = false
                        viewModel.changeModeState(context,false)

                        changeDarkMode(false)

                              },
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Dark", color = Color.Black)
                    },
                    onClick = {
                        isDropDownMenuExpanded = false
                        viewModel.changeModeState(context,true)

                        changeDarkMode(true)
                    })


            }
        }

    }
}