package com.android.sellacha.setting.model

data class Data(
    val amount: Double,
    val category: Category,
    val category_id: Int,
    val created_at: String,
    val id: Int,
    val order_no: String,
    val payment_status: Int,
    val plan: Plan,
    val plan_id: Int,
    val status: Int,
    val tax: Double,
    val trx: String,
    val updated_at: String,
    val user_id: Int,
    val will_expire: String
)