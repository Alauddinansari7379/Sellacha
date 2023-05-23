package com.android.sellacha.Transaction.Model

data class Getway(
    val category_id: String,
    val content: String,
    val created_at: String,
    val id: Int,
    val method: Method,
    val status: String,
    val updated_at: String,
    val user_id: String
)