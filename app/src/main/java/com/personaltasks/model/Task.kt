package com.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.personaltasks.model.Constant.INVALID_TASK_ID
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int? = INVALID_TASK_ID,
    var title: String = "",
    var description: String = "",
    var date: LocalDate = LocalDate.now(),
    var done: Boolean
) : Parcelable
