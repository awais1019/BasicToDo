package com.example.basictodoapp

import TaskViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basictodoapp.databinding.ActivityMainBinding
import com.example.basictodoapp.db.Task
import com.example.basictodoapp.db.TaskDataBase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var popupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val taskDao = TaskDataBase.getDatabase(this).getTaskDao()
        val repository = Repository(taskDao)
        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        // Setup RecyclerView
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            val adapter = RvAdapter(
                tasks,
                onDeleteClick = { task ->
                    taskViewModel.deleteTask(task)
                }
            )
            binding.recycleView.adapter = adapter
        })

        binding.btnAdd.setOnClickListener {
            val view = showAddScreen()
            val btn = view.findViewById<Button>(R.id.save)
            btn.setOnClickListener {
                val newTask = handleAdd(view)
                if (newTask != null) {
                    taskViewModel.upsertTask(newTask)
                    popupWindow.dismiss()
                }
            }
        }

        val day = getCurrentDay()
        val date = getCurrentDate()
        binding.textViewDate.text = "$day, $date"
    }

    private fun showAddScreen(): View {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val focusable = true
        popupWindow = PopupWindow(popupView, width, height, focusable)
        val rootView = findViewById<View>(android.R.id.content)
        popupWindow.showAtLocation(rootView, android.view.Gravity.CENTER, 400, 23)
        return popupView
    }

    private fun handleAdd(popupView: View): Task? {
        val editTextTask: EditText = popupView.findViewById(R.id.editTextAddTask)
        val taskText = editTextTask.text.toString()
        return if (taskText.isNotEmpty()) {
            Task(task = taskText)
        } else {
            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
            null
        }
    }


    private fun getCurrentDay(): String {
        val calendar = Calendar.getInstance()
        val daysOfWeek = arrayOf(
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        )
        return daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
}
