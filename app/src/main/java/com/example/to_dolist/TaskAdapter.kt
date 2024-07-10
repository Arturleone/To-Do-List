package com.example.to_dolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasksList: MutableList<String>,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksList[position]
        holder.textViewTask.text = task
        holder.buttonEdit.setOnClickListener { onEditClick(position) }
        holder.buttonDelete.setOnClickListener { onDeleteClick(position) }
    }

    override fun getItemCount() = tasksList.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
        val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }
}
