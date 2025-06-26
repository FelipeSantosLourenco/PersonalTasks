package com.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.databinding.TileTaskBinding
import com.personaltasks.model.Task
import com.personaltasks.ui.OnHistoryTaskClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryTaskAdapter(
    private val taskList: MutableList<Task>,
    private val onHistoryTaskClickListener: OnHistoryTaskClickListener
): RecyclerView.Adapter<HistoryTaskAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(ttb: TileTaskBinding): RecyclerView.ViewHolder(ttb.root) {
        val titleTv: TextView = ttb.tituloTv
        val descriptionTv: TextView = ttb.descricaoTv
        val dateTv: TextView = ttb.dataLimiteTv
        val done: TextView = ttb.concluidaTv

        init {
            ttb.root.setOnClickListener { onHistoryTaskClickListener.onHistoryTaskClick(adapterPosition) }

            ttb.root.setOnCreateContextMenuListener{ menu, v, menuInfo ->
                (onHistoryTaskClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_history, menu)

                menu.findItem(R.id.view_task_mi).setOnMenuItemClickListener {
                    onHistoryTaskClickListener.onHistoryTaskClick(adapterPosition)
                    true
                }

                menu.findItem(R.id.reactivate_task_mi).setOnMenuItemClickListener {
                    onHistoryTaskClickListener.onReactivateTaskClick(adapterPosition)
                    true
                }

                menu.findItem(R.id.delete_mi).setOnMenuItemClickListener {
                    onHistoryTaskClickListener.onDeleteTaskClick(adapterPosition)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HistoryViewHolder = HistoryViewHolder(
        TileTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    // chamado sempre que os dados de um holder (célula) precisam ser preenchidos ou trocados
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        taskList[position].let{ task ->
            with(holder) {
                val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                titleTv.text = task.title
                descriptionTv.text = task.description
                dateTv.text = fmt.format(Date(task.date))

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