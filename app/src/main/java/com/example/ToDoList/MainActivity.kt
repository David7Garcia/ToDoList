package com.example.ToDoList

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.*
import android.widget.Toast.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import java.io.PrintStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permission()
        readFile()
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val txtInstruction = findViewById<TextView>(R.id.txtInstruction)
        btnAdd.setOnClickListener { writeFile() }
        val ListView = findViewById<ListView>(R.id.txtList)
        ListView.setOnItemLongClickListener { parent, view, position, id ->
            deleteItem(position)
            return@setOnItemLongClickListener true
        }

    }

//funcion de escritura y creacion del archivo ToDoList.txt
    fun writeFile() {
        val data = findViewById<EditText>(R.id.txtTask)
        // validacion de que el campo edittext no este vacio y cree tareas innecesarias
        if (data.text.toString() != "") {
            val output = PrintStream(openFileOutput("toDoList.txt", MODE_APPEND))
            output.println(data.text.toString())
            output.close()
            data.setText("")
            readFile()
        } else {
            Toast.makeText(this, "you didnt write a task", LENGTH_LONG).show()
        }
    }
//funcion de lectura del archivo ToDoList.txt en caso de que no exista enviamos un Try que retorna un texto en este caso "" en blanco
    fun readFile(): String {
        var texto = ""
        try {
            val input = Scanner(openFileInput("toDoList.txt"))
            //chaining
            var data = mutableListOf(input.nextLine())
            val toDoList = findViewById<ListView>(R.id.txtList)
            // indexamos la lista mutable (sin embargo no se ve reflejado en el TXT es solo para la manipulacion de datos
            while (input.hasNextLine()) {
                var i=data.size
                data.add(i,input.nextLine().toString())
                i--
            }
// usamos el ArrayAdapter ya que al no ser una presentacion personalizada en el ListView no es nesesario un Adaptador personalizado
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
            toDoList.adapter = adapter
            input.close()

        } catch (e: Exception) {
            // Nada
        }
        return texto
    }
// "eliminamos" el contenido de ToDoList.TXT ya que lo que realmente se hace es cargar una lista mutable, a esta lista se le eliminima la posicion del indice
// en la cual tenemos el texo a eliminar, luego de esto con un MODE_PRIVATE Borramos la informacion de ToDoList(si por un momento no hay conservacion de datos) y luego cargamos nuevamente el
// archivo ToDoList.txt con la nueva lista ( la cual ya tiene eliminado el elemento) y leemos nuevamente el archivo
    fun deleteItem(position:Int) {
        val input = Scanner(openFileInput("toDoList.txt"))
        //chaining
        var data = mutableListOf(input.nextLine())
        // cargamos la lista
        while (input.hasNextLine()) {
            data.add(input.nextLine().toString())
        }
        // eliminamos el elemento seleccionado por posicion
        data.removeAt(position)
        // eliminamos el contenido de ToDoList.txt
        val output = PrintStream(openFileOutput("toDoList.txt", MODE_PRIVATE))
        output.close()
        // cargamos nuevamente el archivo ToDoList.txt a partir de la lista nueva
        for (i:Int in data.indices)
        {
            val output = PrintStream(openFileOutput("toDoList.txt", MODE_APPEND))
            output.println(data[i].toString())
            output.close()
        }
        //volvemos a cargar el llenado de la ListView
        val toDoList = findViewById<ListView>(R.id.txtList)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        toDoList.adapter = adapter
        input.close()
        makeText(this, "Task delete or complete", LENGTH_SHORT).show()

    }

// esta fue una funcion que estuve mirando la cual permite solicitar los permisos a escritura (con fines de entender espero no afecte el comportamiento de la app)
// las clases adicionales fueron creadas con el fin de ver por medio de la libreria ROOM el uso de un sistema de datos Indexado (pero a falta de tiempo no lo implemente mas)
    fun permission() {

        fun isExternalStorageWritable(): Boolean {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        }

        if (isExternalStorageWritable() == true) {

            if ((ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) !=
                        PackageManager.PERMISSION_GRANTED
                        ) || (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !=
                        PackageManager.PERMISSION_GRANTED
                        )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
            }
        }
    }
}




