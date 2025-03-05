package com.example.easydoc_app

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easydoc_app.data.model.Task

class TaskAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_2, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.description
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskTitle: TextView = itemView.findViewById(R.id.text1)
        var taskDescription: TextView = itemView.findViewById(R.id.text2)
    }
}