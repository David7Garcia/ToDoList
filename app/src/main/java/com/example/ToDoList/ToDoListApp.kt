package com.example.ToDoList

import android.app.Application
import androidx.room.Room

class ToDoListApp : Application(){

    val room = Room.databaseBuilder(this,ToDoListDB::class.java,"ToDoList").build()

}