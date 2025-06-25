package com.personaltasks.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.personaltasks.R
import com.personaltasks.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private val ahb: ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ahb.root)

        setSupportActionBar(ahb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Personal tasks"

    }
}