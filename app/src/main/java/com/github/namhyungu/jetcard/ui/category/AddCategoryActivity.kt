package com.github.namhyungu.jetcard.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme
import dagger.hilt.android.AndroidEntryPoint

fun launchAddCategoryActivity(context: Context) {
    context.startActivity(createAddCategoryActivityIntent(context))
}

fun createAddCategoryActivityIntent(context: Context): Intent {
    return Intent(context, AddCategoryActivity::class.java)
}

@AndroidEntryPoint
class AddCategoryActivity : AppCompatActivity() {
    private val viewModel: AddCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCardTheme {
                AddCategoryScreen(
                    onNavigateUpClick = {
                        finish()
                    },
                    onCategoryAdd = { category ->
                        viewModel.addCategory(category)
                        finish()
                    }
                )
            }
        }
    }
}