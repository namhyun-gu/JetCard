package com.github.namhyungu.jetcard.ui.card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme
import com.github.namhyungu.jetcard.util.extra
import dagger.hilt.android.AndroidEntryPoint

fun launchCardActivity(context: Context, categoryId: Int) {
    context.startActivity(createCardActivityIntent(context, categoryId))
}

fun createCardActivityIntent(context: Context, categoryId: Int): Intent {
    val intent = Intent(context, CardActivity::class.java)
    intent.putExtra(CardActivity.EXTRA_CATEGORY_ID, categoryId)
    return intent
}

@AndroidEntryPoint
class CardActivity : AppCompatActivity() {
    private val viewModel: CardViewModel by viewModels()
    private val categoryId: Int by extra(EXTRA_CATEGORY_ID, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCardTheme {
                val category by viewModel.category.observeAsState()
                val cards by viewModel.cards.observeAsState()

                CardScreen(
                    category,
                    cards,
                    onNavigateUpClick = {
                        finish()
                    },
                    onCardActionClick = { id, action ->
                        viewModel.updateCard(id, action)
                    }
                ) {
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCategory(categoryId)
        viewModel.getCards(categoryId)
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "category_id"
    }
}