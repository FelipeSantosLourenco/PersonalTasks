package com.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.databinding.TileTaskBinding
import com.personaltasks.model.Task
import com.personaltasks.ui.OnTaskClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val onTaskClickListener: OnTaskClickListener
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(ttb: TileTaskBinding): RecyclerView.ViewHolder(ttb.root){
        val titleTv: TextView = ttb.tituloTv
        val descriptionTv: TextView = ttb.descricaoTv
        val dateTv: TextView = ttb.dataLimiteTv
        val done: TextView = ttb.concluidaTv
        val priority: TextView = ttb.prioridadeTv

        init {
            priority.append("alta")
            priority.append("média")
            priority.append("baixa")

            // cria o menu de contexto para cada célula associada a um novo holder
            ttb.root.setOnCreateContextMenuListener{ menu, v, menuInfo ->
                (onTaskClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_main, menu)

                menu.findItem(R.id.edit_task_mi).setOnMenuItemClickListener {
                    onTaskClickListener.onEditTaskMenuItemClick(adapterPosition)
                    true
                }

                menu.findItem(R.id.remove_task_mi).setOnMenuItemClickListener {
                    onTaskClickListener.onRemoveTaskMenuItemClick(adapterPosition)
                    true
                }

                menu.findItem(R.id.view_task_mi).setOnMenuItemClickListener {
                    onTaskClickListener.onViewTaskMenuItemClick(adapterPosition)
                    true
                }
            }

            // seta listener de clique curto na célula de um novo holder
            ttb.root.setOnClickListener { onTaskClickListener.onTaskClick(adapterPosition) }
        }
    }

    // chamado quando um novo holder (célula) precisa ser criado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TaskViewHolder = TaskViewHolder(
                TileTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    // chamado sempre que os dados de um holder (célula) precisam ser preenchidos ou trocados
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        taskList[position].let{ task ->
            with(holder) {
                val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                titleTv.text = task.title
                descriptionTv.text = task.description
                dateTv.text = fmt.format(Date(task.date))
                priority.text = task.priority

                if (task.done) {
                    done.text = "Concluída"
                } else {
                    done.text = "Pendente"
                }
            }
        }
    }

    override fun getItemCount(): Int = taskList.size
}
