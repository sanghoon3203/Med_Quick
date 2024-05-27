package com.example.quick_med

data class Medicine(
    val name: String,
    val description: String,
    val imageUrl: String?,
    val dosage: String,
    val sideEffects: String
)
