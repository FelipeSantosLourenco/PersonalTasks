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
import java.util.Calendar

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
                concluidaCb.isChecked = it.done

                val cal = Calendar.getInstance().apply {
                    timeInMillis = it.date
                }
                dataLimiteEt.updateDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )

                // verifica se é somente vizualização
                val viewTask = intent.getBooleanExtra(EXTRA_VIEW_TASK, false)
                if (viewTask) {
                    supportActionBar?.subtitle = "Visualizar task"
                    tituloEt.isEnabled = false
                    descricaoEt.isEnabled = false
                    dataLimiteEt.isEnabled = false
                    concluidaCb.isEnabled = false
                    salvarBt.visibility = View.GONE
                }
            }
        }

        with(atb) {
            salvarBt.setOnClickListener{
                val year: Int = dataLimiteEt.year
                val month: Int = dataLimiteEt.month
                val day: Int = dataLimiteEt.dayOfMonth
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                val dateMillis = calendar.timeInMillis

                Task(
                    // se recebeu a task, matém o ID, senão gera um novo
                    receivedTask?.id?:hashCode(),
                    tituloEt.text.toString().trim(),
                    descricaoEt.text.toString().trim(),
                    date = dateMillis,
                    concluidaCb.isChecked
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
