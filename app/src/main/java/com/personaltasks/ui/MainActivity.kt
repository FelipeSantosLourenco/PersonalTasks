package com.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.personaltasks.R
import com.personaltasks.adapter.TaskAdapter
import com.personaltasks.controller.MainController
import com.personaltasks.databinding.ActivityMainBinding
import com.personaltasks.model.Constant.EXTRA_TASK
import com.personaltasks.model.Constant.EXTRA_VIEW_TASK
import com.personaltasks.model.Task

class MainActivity : AppCompatActivity(), OnTaskClickListener {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val taskList: MutableList<Task> = mutableListOf()

    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(taskList, this)
    }

    private lateinit var carl:ActivityResultLauncher<Intent>

    private val mainController: MainController by lazy {
        MainController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Personal tasks"

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(EXTRA_TASK, Task::class.java)
                }
                else {
                    result.data?.getParcelableExtra<Task>(EXTRA_TASK)
                }
                task?.let{ receivedTask ->
                    val position = taskList.indexOfFirst { it.id == receivedTask.id }
                    if (position == -1) {
                        taskList.add(receivedTask)
                        taskAdapter.notifyItemInserted(taskList.lastIndex)
                        mainController.insertTask(receivedTask)
                    }
                    else{
                        taskList[position] = receivedTask
                        taskAdapter.notifyItemChanged(position)
                        mainController.updateTask(receivedTask)
                    }
                }
            }
        }

        amb.taskRv.adapter = taskAdapter
        amb.taskRv.layoutManager = LinearLayoutManager(this)

        fillTaskList()
    }

    override fun onTaskClick(position: Int) {
        Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            putExtra(EXTRA_VIEW_TASK, true)
            startActivity(this)
        }
    }

    override fun onRemoveTaskMenuItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onEditTaskMenuItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    private fun fillTaskList() {
        taskList.clear()

        Thread {
            taskList.addAll(mainController.getTasks())
            taskAdapter.notifyDataSetChanged()
        }.start()
    }
}
