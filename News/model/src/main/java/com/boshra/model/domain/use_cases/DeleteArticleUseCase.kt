package com.boshra.model.domain.use_cases

import com.boshra.model.domain.entities.Article
import com.boshra.model.domain.repo.NewsRepository


class DeleteArticleUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.deleteArticle(article)
    }
}