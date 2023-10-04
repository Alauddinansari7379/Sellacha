package com.android.sellacha.Order.Model

data class Post(
    val created_at: String,
    val featured: Int,
    val id: Int,
    val is_admin: Int,
    val menu_status: Int,
    val name: String,
    val p_id: Any,
    val pivot: Pivot,
    val slug: String,
    val type: String,
    val updated_at: String,
    val user_id: Int
)