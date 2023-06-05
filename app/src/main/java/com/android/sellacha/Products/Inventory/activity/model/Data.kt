package com.android.sellacha.Products.Inventory.activity.model

data class Data(
    val in_stock: Int,
    val out_stock: Int,
    val posts: Posts,
    val status: String,
    val total: Int
)