package com.boshra.model.domain.use_cases

import com.boshra.model.domain.entities.Article
import com.boshra.model.domain.repo.NewsRepository
import kotlinx.coroutines.flow.Flow


class GetBreakingNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return repository.getSavedNews()
    }
}