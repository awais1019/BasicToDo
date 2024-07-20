package com.example.basictodoapp

import TaskViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basictodoapp.db.Task
import com.example.basictodoapp.db.TaskDataBase

class MainActivity : AppCompatActivity() {

    // Define the taskViewModel as a property of MainActivity
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskDao = TaskDataBase.getDatabase(this).getTaskDao()
        val repository = Repository(taskDao)
        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        val recycleView = findViewById<RecyclerView>(R.id.recycleView)

        recycleView.layoutManager = LinearLayoutManager(this)
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            val adapter = RvAdapter(
                tasks,
                onUpdateClick = { task ->
                    taskViewModel.updateTask(task)
                },
                onDeleteClick = { task ->
                    taskViewModel.deleteTask(task)
                }
            )
            recycleView.adapter = adapter
        })

        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            showAddScreen()
        }
    }

    private fun showAddScreen() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        val rootView = findViewById<View>(android.R.id.content)
        popupWindow.showAtLocation(rootView, android.view.Gravity.CENTER, 0, 0)

        val btnSave: Button = popupView.findViewById(R.id.save)
        btnSave.setOnClickListener {

            if(handleSave(popupView))
            {
                popupWindow.dismiss()

            }
            else
            {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSave(popupView: View):Boolean {
        var istrue=false;
        val editTextTask: EditText = popupView.findViewById(R.id.editTextAddTask)
        val taskText = editTextTask.text.toString()
        if (taskText.isNotEmpty()) {
            val newTask = Task(id = 0, task = taskText, status = 0)
            taskViewModel.upsertTask(newTask)
            istrue=true
        } else {

            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
        }
        return istrue
    }
}
