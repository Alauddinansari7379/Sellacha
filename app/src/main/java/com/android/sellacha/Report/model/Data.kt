package com.android.sellacha.Report.model

data class Data(
    val amount_cancel: Int,
    val amount_completed: String,
    val amount_proccess: String,
    val amounts: String,
    val canceled: Int,
    val completed: Int,
    val end: String,
    val orders: Orders,
    val proccess: Int,
    val request: Request,
    val start: String,
    val total: Int
)