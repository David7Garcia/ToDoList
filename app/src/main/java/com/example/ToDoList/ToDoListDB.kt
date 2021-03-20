package com.example.ToDoList

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ToDoList::class],
    version = 1
)

abstract class ToDoListDB : RoomDatabase(){

    abstract fun ToDoListDao(): ToDoListDao

}