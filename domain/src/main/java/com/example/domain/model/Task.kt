package com.example.todo.model.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tasks")

data class Task(

    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    var taskName:String? = null,
    var taskDesc:String? =null,
    var isDone:Boolean = false,
    var dateTime: Long? = null
)