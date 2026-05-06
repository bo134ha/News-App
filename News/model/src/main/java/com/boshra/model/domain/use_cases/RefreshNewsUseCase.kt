package com.boshra.model.domain.use_cases

import com.boshra.model.domain.repo.NewsRepository

class RefreshNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke() {

        val remoteArticles = repository.fetchBreakingNewsFromRemote()

        repository.refreshNews(remoteArticles)
    }
}