package com.android.sellacha.setting.Domain.model

data class Request(
    val created_at: String,
    val domain: String,
    val domain_id: Int,
    val id: Int,
    val status: Int,
    val updated_at: String,
    val user_id: Int
)