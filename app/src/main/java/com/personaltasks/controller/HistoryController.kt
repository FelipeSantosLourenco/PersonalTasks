package com.personaltasks.controller

import android.os.Message
import com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import com.personaltasks.model.Task
import com.personaltasks.model.TaskDao
import com.personaltasks.model.TaskFirebaseDb
import com.personaltasks.ui.HistoryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryController(private val historyActivity: HistoryActivity) {
    private val taskDao: TaskDao = TaskFirebaseDb()
    private val databaseCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getHistoryTasks() {
        databaseCoroutineScope.launch {
            val taskList = taskDao.retrieveTasks()

            val history = taskList.filter { !it.active }

            historyActivity.getTasksHandler.sendMessage(Message().apply {
                data.putParcelableArray(EXTRA_TASK_ARRAY, history.toTypedArray())
            })
        }
    }

    fun reactivateTask(task: Task) {
        databaseCoroutineScope.launch {
            task.active = true
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        databaseCoroutineScope.launch {
            taskDao.deleteTask(task)
        }
    }
}