package com.personaltasks.ui

sealed interface OnTaskClickListener {
    fun onTaskClick(position: Int)

    fun onRemoveTaskMenuItemClick(position: Int)
    fun onEditTaskMenuItemClick(position: Int)
    fun onViewTaskMenuItemClick(position: Int)
}
