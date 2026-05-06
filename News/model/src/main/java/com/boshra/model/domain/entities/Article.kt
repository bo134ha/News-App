package com.boshra.model.domain.entities

data class Article(
    val id: Int? = null,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val source: String?,
    val content: String?
)