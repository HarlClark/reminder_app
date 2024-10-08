package com.example.secondapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskList: RecyclerView
    private lateinit var addTaskButton: Button
    private lateinit var currentDate: TextView
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        currentDate = findViewById(R.id.currentDate)
        taskList = findViewById(R.id.taskList)
        addTaskButton = findViewById(R.id.addTaskButton)

        // Initialize RecyclerView
        taskAdapter = TaskAdapter(tasks)
        taskList.layoutManager = LinearLayoutManager(this)
        taskList.adapter = taskAdapter

        // Set current date
        val today = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        currentDate.text = "Today: $today"

        // Handle "Add Task" button click
        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        // Inflate custom dialog layout
        val dialogView = layoutInflater.inflate(R.layout.add_task_dialog, null)
        val taskNameInput = dialogView.findViewById<EditText>(R.id.taskNameInput)

        // Show dialog
        AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val taskName = taskNameInput.text.toString()
                if (taskName.isNotEmpty()) {
                    val newTask = Task(taskName)
                    tasks.add(newTask)
                    taskAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}
