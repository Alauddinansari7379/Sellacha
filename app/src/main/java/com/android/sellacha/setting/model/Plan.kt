package com.android.sellacha.setting.model

data class Plan(
    val created_at: String,
    val custom_domain: Int,
    val `data`: String,
    val days: Int,
    val description: String,
    val featured: Int,
    val id: Int,
    val is_default: Int,
    val is_trial: Int,
    val name: String,
    val price: Int,
    val status: Int,
    val updated_at: String
)