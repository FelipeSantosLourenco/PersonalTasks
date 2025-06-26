package com.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.personaltasks.R
import com.personaltasks.adapter.HistoryTaskAdapter
import com.personaltasks.adapter.TaskAdapter
import com.personaltasks.controller.HistoryController
import com.personaltasks.controller.MainController
import com.personaltasks.databinding.ActivityHistoryBinding
import com.personaltasks.model.Constant.EXTRA_TASK
import com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import com.personaltasks.model.Constant.EXTRA_VIEW_TASK
import com.personaltasks.model.Task

class HistoryActivity : AppCompatActivity(), OnHistoryTaskClickListener {
    private val ahb: ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }

    private val taskList: MutableList<Task> = mutableListOf()

    private val historyAdapter: HistoryTaskAdapter by lazy {
        HistoryTaskAdapter(taskList, this)
    }

    private val historyController: HistoryController by lazy {
        HistoryController(this)
    }

    companion object {
        const val GET_TASKS_MESSAGE = 1
        const val GET_TASKS_INTERVAL = 2000L
    }

    val getTasksHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what == GET_TASKS_MESSAGE) {
                historyController.getHistoryTasks()

                sendMessageDelayed(
                    obtainMessage().apply { what = GET_TASKS_MESSAGE },
                    GET_TASKS_INTERVAL
                )
            } else {
                val taskArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    msg.data?.getParcelableArray(EXTRA_TASK_ARRAY, Task::class.java)
                }
                else {
                    msg.data?.getParcelableArray(EXTRA_TASK_ARRAY)
                }
                taskList.clear()
                taskArray?.forEach { taskList.add(it as Task) }
                historyAdapter.notifyDataSetChanged()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ahb.root)

        setSupportActionBar(ahb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Histórico"

        ahb.taskRv.adapter = historyAdapter
        ahb.taskRv.layoutManager = LinearLayoutManager(this)

        getTasksHandler.sendMessageDelayed(
            Message().apply { what = GET_TASKS_MESSAGE }, GET_TASKS_INTERVAL
        )
    }

    override fun onHistoryTaskClick(position: Int) {
        Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            putExtra(EXTRA_VIEW_TASK, true)
            startActivity(this)
        }
    }

    override fun onReactivateTaskClick(position: Int) {
        val task = taskList[position]
        taskList.removeAt(position)
        historyController.reactivateTask(task)
        historyAdapter.notifyItemRemoved(position)

        Toast.makeText(this,
            "Task restaurada!",
            Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteTaskClick(position: Int) {
        val task = taskList[position]
        taskList.removeAt(position)
        historyController.deleteTask(task)
        historyAdapter.notifyItemRemoved(position)

        Toast.makeText(this,
            "Task deletada!",
            Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.back_home_mi -> {
                finish()
                true
            }

            else -> { false }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser == null) finish()
    }
}