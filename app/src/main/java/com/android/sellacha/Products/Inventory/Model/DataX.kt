package com.android.sellacha.Products.Inventory.Model

data class DataX(
    val id: Int,
    val sku: String,
    val stock_manage: String,
    val stock_qty: String,
    val stock_status: String,
    val term: Term,
    val term_id: String
)