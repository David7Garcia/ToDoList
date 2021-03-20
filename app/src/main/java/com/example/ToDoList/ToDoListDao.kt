package com.example.ToDoList

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoListDao {

    @Query("SELECT * FROM ToDoList")
    fun getAll():  LiveData<List<ToDoList>>

    @Insert
    fun Insert(Task : List<ToDoList>)

    @Delete
    fun deleteById(Id :ToDoList)



}