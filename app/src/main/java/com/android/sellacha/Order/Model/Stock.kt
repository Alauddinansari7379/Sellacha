package com.android.sellacha.Order.Model

data class Stock(
    val id: Int,
    val sku: String,
    val stock_manage: String,
    val stock_qty: String,
    val stock_status: String,
    val term_id: String
)