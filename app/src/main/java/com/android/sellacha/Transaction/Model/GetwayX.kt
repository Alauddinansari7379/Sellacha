package com.android.sellacha.Transaction.Model

data class GetwayX(
    val created_at: String,
    val featured: String,
    val id: Int,
    val is_admin: String,
    val menu_status: String,
    val name: String?=null,
    val p_id: Any,
    val slug: String,
    val type: String,
    val updated_at: String,
    val user_id: String
)