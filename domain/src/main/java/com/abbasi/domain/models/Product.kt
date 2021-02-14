package com.abbasi.domain.models

data class Product (
    val id: String,
    val categoryId: String,
    val name: String,
    val url: String,
    val description: String,
    val price: String,
    val currency: String
)