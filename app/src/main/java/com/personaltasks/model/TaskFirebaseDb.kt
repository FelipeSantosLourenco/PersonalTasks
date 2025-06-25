package com.personaltasks.model

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class TaskFirebaseDb: TaskDao {
    private val databaseReference = Firebase.database.getReference("tasks")
    private val tasks = mutableListOf<Task>()

    init {
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val task = snapshot.getValue<Task>()
                task?.let { newTask ->
                    if (!tasks.any() { it.id == newTask.id }) {
                        tasks.add(newTask)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val task = snapshot.getValue<Task>()
                task?.let { editedTask ->
                    tasks[tasks.indexOfFirst { it.id == editedTask.id }] = editedTask
                }
            }

        }

        )
    }

}