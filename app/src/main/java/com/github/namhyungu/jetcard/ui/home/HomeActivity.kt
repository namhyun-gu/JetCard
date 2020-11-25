package com.github.namhyungu.jetcard.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
                HomeScreen(
                    viewModel,
                    onNewCategoryClick = {
                        launchAddCategoryActivity(this)
                    },
                    onCategoryClick = { category ->
                        launchCategoryActivity(this, category.id)
                    },
                    onCategoryPinnedChange = { category ->
                        viewModel.updateCategoryPin(category)
                    }
                )
            }
        }
    }
}