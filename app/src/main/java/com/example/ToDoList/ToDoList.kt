package com.example.ToDoList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoList (
    @PrimaryKey(autoGenerate = true)
    val Id: Int,

    val task: String

)


