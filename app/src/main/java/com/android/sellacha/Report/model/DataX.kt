package com.android.sellacha.Report.model

data class DataX(
    val category_id: String,
    val created_at: String,
    val customer: Customer,
    val customer_id: String,
    val id: Int,
    val order_items_count: String,
    val order_no: String,
    val order_type: String,
    val payment_status: String,
    val s_date: Any,
    val shipping: String,
    val status: String,
    val tax: String,
    val total: String,
    val transaction_id: String,
    val updated_at: String,
    val user_id: String
)