package com.android.sellacha.Order.Model

data class Item(
    val attributes: String,
    val id: Int,
    val options: String,
    val preview: String,
    val price: String,
    val qty: String,
    val subtotal: String,
    val term_id: String,
    val term_title: String,
    val final_total: String
)