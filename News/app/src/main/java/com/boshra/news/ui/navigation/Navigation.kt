package com.boshra.news.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boshra.model.data.repo.NewsRepositoryImpl
import com.boshra.model.domain.use_cases.DeleteArticleUseCase
import com.boshra.model.domain.use_cases.GetBreakingNewsUseCase
import com.boshra.model.domain.use_cases.RefreshNewsUseCase
import com.boshra.news.ui.screens.ArticleDetailsScreen
import com.boshra.news.ui.screens.NewsListScreen
import com.boshra.news.view_model.NewsViewModel
import com.boshra.news.view_model.NewsViewModelFactory

@Composable
fun NewsAppNavigation(repository: NewsRepositoryImpl) {
    val navController: NavHostController = rememberNavController()


    val getBreakingNewsUseCase by lazy { GetBreakingNewsUseCase(repository) }
    val refreshNewsUseCase by lazy { RefreshNewsUseCase(repository) }
    val deleteArticleUseCase by lazy { DeleteArticleUseCase(repository) }

    val viewModel: NewsViewModel = viewModel(
        factory = NewsViewModelFactory(
            getBreakingNewsUseCase = getBreakingNewsUseCase,
            refreshNewsUseCase = refreshNewsUseCase,
            deleteArticleUseCase = deleteArticleUseCase
        )
    )

    NavHost(navController = navController, startDestination = Screen.NewsList.route) {
        composable(Screen.NewsList.route) {
            NewsListScreen(navController, viewModel)
        }

        composable(Screen.ArticleDetails.route) {
            ArticleDetailsScreen(navController,viewModel)
        }
    }
}