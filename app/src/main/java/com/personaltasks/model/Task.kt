package com.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.personaltasks.model.Constant.INVALID_TASK_ID
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int? = INVALID_TASK_ID,
    var title: String = "",
    var description: String = "",
    var date: Long = 0L,
    var done: Boolean = false,
    var active: Boolean = true
) : Parcelable {
    override fun toString(): String {
        val fmt = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
        return "$title - $description - ${fmt.format(Date(date))}"
    }
}
