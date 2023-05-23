package com.android.sellacha.Products.categories.Model

data class DataX(
    val created_at: String,
    val featured: String,
    val id: Int,
    val is_admin: String,
    val menu_status: String,
    val name: String,
    val rating: String,
    val email: String,
    val varitions: String,
    val p_id: Any,
    val preview: Preview?=null,
    val slug: String,
    val type: String,
    val updated_at: String,
    val user_id: String
)