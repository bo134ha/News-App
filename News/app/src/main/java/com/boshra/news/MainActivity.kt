package com.boshra.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boshra.model.data.local.DatabaseClient
import com.boshra.model.data.remote.RetrofitClient
import com.boshra.model.data.repo.NewsRepositoryImpl
import com.boshra.news.ui.navigation.NewsAppNavigation
import com.boshra.news.ui.theme.NewsTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val database by lazy { DatabaseClient.getDatabase(this) }
    private val apiService by lazy { RetrofitClient.apiService }
    val repository by lazy { NewsRepositoryImpl(apiService, database.articleDao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                NewsAppNavigation(repository = repository)
            }
        }
    }
}
