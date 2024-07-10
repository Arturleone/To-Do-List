package com.example.to_dolist

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTask: EditText
    private lateinit var botaoadd: FloatingActionButton
    private lateinit var recyclerViewTasks: RecyclerView

    private lateinit var tasksList: MutableList<String>
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        // Inicializar as views
        editTextTask = findViewById(R.id.editTextTask)
        botaoadd = findViewById(R.id.botaoadd)
        recyclerViewTasks = findViewById(R.id.recyclerView)

        // Inicializar a lista de tarefas e o adaptador
        tasksList = mutableListOf()
        adapter = TaskAdapter(tasksList, this::showEditTaskDialog, this::deleteTask)

        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = adapter

        // Adicionar tarefa ao clicar no botão
        botaoadd.setOnClickListener {
            val task = editTextTask.text.toString()
            if (task.isNotEmpty()) {
                tasksList.add(task)
                adapter.notifyItemInserted(tasksList.size - 1)
                editTextTask.text.clear()
            } else {
                Toast.makeText(this, "Digite uma tarefa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditTaskDialog(position: Int) {
        val task = tasksList[position]
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_task, null)
        val editTextTask = dialogView.findViewById<EditText>(R.id.editTextTask)
        editTextTask.setText(task)

        AlertDialog.Builder(this)
            .setTitle("Editar Tarefa")
            .setView(dialogView)
            .setPositiveButton("Salvar") { _, _ ->
                val newTask = editTextTask.text.toString()
                if (newTask.isNotEmpty()) {
                    tasksList[position] = newTask
                    adapter.notifyItemChanged(position)
                } else {
                    Toast.makeText(this, "A tarefa não pode estar vazia", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteTask(position: Int) {
        tasksList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}