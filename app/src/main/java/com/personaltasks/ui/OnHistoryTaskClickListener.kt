package com.personaltasks.ui

sealed interface OnHistoryTaskClickListener {
    fun onHistoryTaskClick(position: Int)
    fun onReactivateTaskClick(position: Int)
    fun onDeleteTaskClick(position: Int)
}