package com.android.sellacha.Report.model

data class Customer(
    val created_at: String,
    val created_by: String,
    val domain_id: String,
    val email: String,
    val id: Int,
    val mobile: Any,
    val name: String,
    val updated_at: String
)