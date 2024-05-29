package com.example.todo.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.R
import com.example.todo.model.database.TasksDataBase
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.ui.theme.backgroundColor

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    MaterialTheme.colorScheme.background.toArgb(),
                    MaterialTheme.colorScheme.background.toArgb()
                )
            )
            ToDoTheme {

                TasksDataBase.init(applicationContext)


                SplashScreen()

               Handler(Looper.getMainLooper()).postDelayed({

                    var intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()


                },2000)






            }
        }
    }
}

@Preview
@Composable
fun SplashScreen(){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background)
    ) {
        
        Image(
            painter = painterResource(id = R.drawable.logo_2x),
            contentDescription ="app logo",
            modifier = Modifier.fillMaxWidth(0.46f),
            contentScale = ContentScale.FillWidth)
        
    }
}