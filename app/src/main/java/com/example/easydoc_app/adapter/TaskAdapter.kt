package com.example.easydoc_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easydoc_app.R
import com.example.easydoc_app.data.model.Task



class TaskAdapter(private val taskList: MutableList<Task>,
                  private val context: Context
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val DOUBLE_CLICK_TIME_DELTA: Long = 300 // 300ms für Doppelklick
    private var lastClickTime: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.description

        holder.itemView.setOnClickListener {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Doppelklick erkannt -> Wechsel zur Detailseite
                val intent = Intent(context, com.example.easydoc_app.TaskDetailActivity::class.java).apply {
                    putExtra("task_id", task.getId()) // ID explizit setzen
                    putExtra("task_title", task.getTitle())
                    putExtra("task_description", task.getDescription())
                    putExtra("task_status", task.getStatus())
                }
                context.startActivity(intent)

            }
            lastClickTime = clickTime
        }
    }
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        var taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
    }
}
