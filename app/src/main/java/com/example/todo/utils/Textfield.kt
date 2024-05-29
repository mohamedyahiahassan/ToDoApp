package com.example.todo.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import  androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.res.colorResource
import com.example.todo.ui.theme.backgroundColor
import com.example.todo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTextField(
    textFieldInput:MutableState<String?>,
    placeHolderText:String,
    errorMessage:MutableState<String>){


    TextField(
        value = textFieldInput.value?:"",
        onValueChange ={
            textFieldInput.value = it
        } ,
        modifier = Modifier.fillMaxWidth(1f),
        placeholder = {
            Text(text = placeHolderText, color = colorResource(id = R.color.text_color))
        },
        colors = TextFieldDefaults.colors(

            focusedContainerColor =Color.Transparent,
            unfocusedContainerColor = Color.Transparent,

            ),
        supportingText = {

            if (errorMessage.value.isNotEmpty()){

                Text(
                    text = errorMessage.value,
                    color = Color.Red)
            }



        }
    )
}
