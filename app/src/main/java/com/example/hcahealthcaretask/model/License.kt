package com.example.hcahealthcaretask.model

/** License metadata returned as part of a GitHub repository response. */
data class License(
    val key: String,
    val name: String,
    val node_id: String,
    val spdx_id: String,
    val url: String
)