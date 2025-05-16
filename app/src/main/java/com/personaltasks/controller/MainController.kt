package com.personaltasks.controller

import androidx.room.Room
import com.personaltasks.model.Task
import com.personaltasks.model.TaskDao
import com.personaltasks.model.TaskRoomDb
import com.personaltasks.ui.MainActivity

class MainController(mainActivity: MainActivity) {
    private val taskDao: TaskDao = Room.databaseBuilder(
        mainActivity, TaskRoomDb::class.java, "task-database"
    ).build().taskDao()

    fun insertTask(task: Task){
        Thread {
            taskDao.createTask(task)
        }.start()
    }

    fun getTask(id: Int) = taskDao.retrieveTask(id)
    fun getTasks() = taskDao.retrieveTasks()
    fun updateTask(task: Task) = taskDao.updateTask(task)
    fun removeTask(task: Task) = taskDao.deleteTask(task)
}