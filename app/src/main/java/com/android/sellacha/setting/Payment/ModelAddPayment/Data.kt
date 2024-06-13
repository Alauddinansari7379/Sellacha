package com.android.sellacha.setting.Payment.ModelAddPayment

data class Data(
    val category_id: Int,
    val content: String,
    val created_at: String,
    val id: Int,
    val method: Method,
    val status: String,
    val updated_at: String,
    val user_id: Int
)