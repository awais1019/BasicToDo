package com.example.basictodoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basictodoapp.db.Task

class RvAdapter(
    private var taskList: List<Task>,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<RvAdapter.MyHolder>() {

    inner class MyHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val taskTextView: TextView = item.findViewById(R.id.textViewTask)
        private val deleteImageView: ImageView = item.findViewById(R.id.delete)

        fun bind(taskItem: Task) {
            taskTextView.text = taskItem.task
            deleteImageView.setOnClickListener {
                onDeleteClick(taskItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_design, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {

        return taskList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(taskList[position])
    }


}
