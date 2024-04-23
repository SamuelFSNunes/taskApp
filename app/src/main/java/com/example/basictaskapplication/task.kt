package com.example.basictaskapplication

object TaskManager {
    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun getAllTasks(): List<Task> {
        return tasks.toList()
    }

    fun getPendingTasks(): List<Task> {
        return (tasks.filter { it.status === "Pendente" }).toList()
    }

}

data class Task(
    val title: String,
    val description: String,
    val status: String
)
