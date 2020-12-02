package com.github.namhyungu.jetcard.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import com.github.namhyungu.jetcard.ui.category.launchAddCategoryActivity
import com.github.namhyungu.jetcard.ui.category.launchCategoryActivity
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCardTheme {
                val categories by viewModel.cardCategories.observeAsState()

                HomeScreen(
                    categories,
                    onNewCategoryClick = {
                        launchAddCategoryActivity(this)
                    },
                    onCategoryClick = { category ->
                        launchCategoryActivity(this, category.id)
                    }
                ) { category ->
                    viewModel.updateCategoryPin(category)
                }
            }
        }
    }
}