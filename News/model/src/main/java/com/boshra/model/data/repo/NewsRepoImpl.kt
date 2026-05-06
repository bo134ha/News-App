package com.boshra.model.data.repo

import com.boshra.model.data.local.ArticleEntity
import com.boshra.model.data.remote.ArticleDto
import com.boshra.model.domain.entities.Article
import com.boshra.model.data.local.ArticleDao
import com.boshra.model.data.remote.NewsApiService
import com.boshra.model.domain.repo.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val api: NewsApiService,
    private val dao: ArticleDao
) : NewsRepository {

    override fun getSavedNews(): Flow<List<Article>> {
        return dao.getAllArticles().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchBreakingNewsFromRemote(): List<Article> {
        val response = api.getBreakingNews()
        return response.articles.map { it.toDomain() }
    }

    override suspend fun refreshNews(articles: List<Article>) {
        dao.clearAndInsertArticles(articles.map { it.toEntity() })
    }


    override suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article.toEntity())
    }
}

fun ArticleDto.toDomain(): Article {
    return Article(
        title = title ?: "",
        description = description,
        url = url ?: "",
        urlToImage = urlToImage,
        source = source?.name,
        content = content
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id ?: 0,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        sourceName = source,
        content = content
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        source = sourceName,
        content = content
    )
}