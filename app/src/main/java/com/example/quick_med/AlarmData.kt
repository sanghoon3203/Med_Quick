package com.example.quick_med

data class AlarmData(
    var name: String,
    var hour: Int,
    var minute: Int,
    val repeatDays: BooleanArray
)