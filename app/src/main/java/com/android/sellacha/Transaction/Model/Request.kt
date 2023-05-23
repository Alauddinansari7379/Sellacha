package com.android.sellacha.Transaction.Model

data class Request(
    val attributes: Attributes,
    val cookies: Cookies,
    val files: Files,
    val headers: Headers,
    val query: Query,
    val request: RequestX,
    val server: Server
)