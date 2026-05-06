package com.boshra.news.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boshra.model.domain.use_cases.GetBreakingNewsUseCase
import com.boshra.model.domain.use_cases.RefreshNewsUseCase
import com.boshra.model.domain.use_cases.DeleteArticleUseCase


class NewsViewModelFactory(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(getBreakingNewsUseCase, refreshNewsUseCase, deleteArticleUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}