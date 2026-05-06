package com.boshra.news.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boshra.model.domain.entities.Article
import com.boshra.model.domain.use_cases.GetBreakingNewsUseCase
import com.boshra.model.domain.use_cases.RefreshNewsUseCase
import com.boshra.model.domain.use_cases.DeleteArticleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(
    getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) : ViewModel() {

    val uiState: StateFlow<NewsUiState> = getBreakingNewsUseCase()
        .map { articles ->
            if (articles.isEmpty()) {
                NewsUiState.Loading
            } else {
                NewsUiState.Success(articles)
            }
        }
        .catch { e ->
            emit(NewsUiState.Error(e.localizedMessage ?: "Error while getting data"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NewsUiState.Loading
        )

    // you can use it
    private val _uiEffect = Channel<NewsUiEffect>()
    val uiEffect: Flow<NewsUiEffect> = _uiEffect.receiveAsFlow()

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    init {
        refreshNewsFromRemote()
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun refreshNewsFromRemote() {
        viewModelScope.launch {
            try {
                refreshNewsUseCase()
            } catch (e: Exception) {
                _uiEffect.send(NewsUiEffect.ShowSnackBar(e.localizedMessage ?: "No internet connection"))
            }
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            try {
                deleteArticleUseCase(article)
            } catch (e: Exception) {
                _uiEffect.send(NewsUiEffect.ShowSnackBar(e.localizedMessage ?: "Failed to delete article"))
            }
        }
    }
}

sealed class NewsUiEffect {
    data class ShowSnackBar(val message: String) : NewsUiEffect()
}

sealed class NewsUiState {
    data object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}