package com.personaltasks.model

import android.os.Parcelable
import com.personaltasks.model.Constant.INVALID_TASK_ID
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Task (
    var id: Int? = INVALID_TASK_ID,
    var title: String = "",
    var description: String = "",
    var date: LocalDate = LocalDate.now()
) : Parcelable
