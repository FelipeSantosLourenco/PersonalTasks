package com.personaltasks.controller

import android.os.Message
import androidx.room.Room
import com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import com.personaltasks.model.Task
import com.personaltasks.model.TaskDao
import com.personaltasks.model.TaskFirebaseDb
import com.personaltasks.model.TaskRoomDb
import com.personaltasks.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainController(private val mainActivity: MainActivity) {
    private val taskDao: TaskDao = TaskFirebaseDb()
    private val databaseCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun insertTask(task: Task) {
        databaseCoroutineScope.launch {
            taskDao.createTask(task)
        }
    }

    fun getTask(id: Int) = taskDao.retrieveTask(id)

    fun getTasks() {
        databaseCoroutineScope.launch {
            val taskList = taskDao.retrieveTasks()
            mainActivity.getTasksHandler.sendMessage(Message().apply {
                data.putParcelableArray(EXTRA_TASK_ARRAY, taskList.toTypedArray())
            })
        }
    }

    fun modifyTask(task: Task) {
        databaseCoroutineScope.launch {
            taskDao.updateTask(task)
        }
    }
}