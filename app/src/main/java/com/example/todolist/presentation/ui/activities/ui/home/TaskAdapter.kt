package com.example.todolist.presentation.ui.activities.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.entities.Task
import com.example.todolist.databinding.TaskCellBinding
import com.example.todolist.utils.extensions.shortDate
import com.google.android.gms.tasks.Tasks

class TaskAdapter(private val taskList: MutableList<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = TaskCellBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_cell, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.taskTitle.text = task.title
        holder.binding.taskDescription.text = task.description
        holder.binding.taskDueDate.text = task.dueDate.shortDate()
        if (task.isCompleted) { holder.binding.tasktStatusImage.setImageResource(R.drawable.check_icon) } else { holder.binding.tasktStatusImage.setImageResource(R.drawable.unchecked_icon) }
//        TaskStatus.typeStatus(task.status)?.let {
//            when(it) {
//                TaskStatus.COMPLETED -> holder.binding.tasktStatusImage.setImageResource(R.drawable.check_icon)
//                TaskStatus.PENDING -> holder.binding.tasktStatusImage.setImageResource(R.drawable.unchecked_icon)
//            }
//        }
    }
}