package com.android.sellacha.setting.Domain.model

data class Info(
    val brand_limit: String,
    val category_limit: String,
    val custom_css: Boolean,
    val custom_domain: Boolean,
    val custom_js: Boolean,
    val customer_limit: String,
    val customer_panel: Boolean,
    val facebook_pixel: Boolean,
    val google_analytics: Boolean,
    val gtm: Boolean,
    val inventory: Boolean,
    val live_support: Boolean,
    val location_limit: String,
    val pos: Boolean,
    val product_limit: String,
    val pwa: Boolean,
    val qr_code: Boolean,
    val storage: String,
    val variation_limit: String,
    val whatsapp: Boolean
)