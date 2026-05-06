package com.boshra.model.domain.repo

import com.boshra.model.domain.entities.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getSavedNews(): Flow<List<Article>>

    suspend fun fetchBreakingNewsFromRemote(): List<Article>
    suspend fun refreshNews(articles: List<Article>)
    suspend fun deleteArticle(article: Article)
}