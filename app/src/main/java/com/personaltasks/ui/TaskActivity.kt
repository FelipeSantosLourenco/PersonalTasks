package com.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.personaltasks.databinding.ActivityTaskBinding
import com.personaltasks.model.Constant.EXTRA_TASK
import com.personaltasks.model.Constant.EXTRA_VIEW_TASK
import com.personaltasks.model.Task
import java.time.LocalDate

class TaskActivity : AppCompatActivity() {

    private val atb: ActivityTaskBinding by lazy {
        ActivityTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(atb.root)

        setSupportActionBar(atb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Nova task"

        val receivedTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
        }
        else {
            intent.getParcelableExtra<Task>(EXTRA_TASK)
        }

        receivedTask?.let {
            supportActionBar?.subtitle = "Editar task"
            with(atb) {
                tituloEt.setText(it.title)
                descricaoEt.setText(it.description)

                // datePicker usa index de 0 a 11, então precisa tirar 1
                dataLimiteEt.updateDate(it.date.year, it.date.monthValue - 1, it.date.dayOfMonth)

                // verifica se é somente vizualização
                val viewTask = intent.getBooleanExtra(EXTRA_VIEW_TASK, false)
                if (viewTask) {
                    supportActionBar?.subtitle = "Visualizar task"
                    tituloEt.isEnabled = false
                    descricaoEt.isEnabled = false
                    dataLimiteEt.isEnabled = false
                    salvarBt.visibility = View.GONE
                }
            }
        }

        with(atb) {
            salvarBt.setOnClickListener{
                Task(
                    // se recebeu a task, matém o ID, senão gera um novo
                    receivedTask?.id?:hashCode(),
                    tituloEt.text.toString().trim(),
                    descricaoEt.text.toString().trim(),
                    // datePicker usa index de 0 a 11 para mês
                    LocalDate.of(dataLimiteEt.year, dataLimiteEt.month + 1, dataLimiteEt.dayOfMonth)
                ).let { task ->
                    Intent().apply {
                        putExtra(EXTRA_TASK, task)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
        }

        with(atb) {
            cancelarBt.setOnClickListener{
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }
}
