package com.github.namhyungu.jetcard.ui.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.github.namhyungu.jetcard.ui.card.launchAddCardActivity
import com.github.namhyungu.jetcard.ui.card.launchCardActivity
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme
import com.github.namhyungu.jetcard.util.extra
import dagger.hilt.android.AndroidEntryPoint

fun launchCategoryActivity(context: Context, categoryId: Int) {
    context.startActivity(createCategoryActivityIntent(context, categoryId))
}

fun createCategoryActivityIntent(context: Context, categoryId: Int): Intent {
    val intent = Intent(context, CategoryActivity::class.java)
    intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId)
    return intent
}

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private val viewModel: CategoryViewModel by viewModels()
    private val categoryId: Int by extra(EXTRA_CATEGORY_ID, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCardTheme {
                CategoryScreen(
                    viewModel,
                    onNavigateUpClick = {
                        finish()
                    },
                    onPlayClick = {
                        launchCardActivity(this, categoryId)
                    },
                    onEditCategory = {},
                    onAddCard = {
                        launchAddCardActivity(this, categoryId)
                    },
                    onEditCard = {},
                    onDeleteCard = { card ->
                        viewModel.deleteCard(categoryId, card)
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCategoryWithContents(categoryId)
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "category_id"
    }
}