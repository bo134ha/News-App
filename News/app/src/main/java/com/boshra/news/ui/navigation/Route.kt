package com.boshra.news.ui.navigation

sealed class Screen(val route: String) {
    object NewsList : Screen("news_list")
    object ArticleDetails : Screen("article_details")
}