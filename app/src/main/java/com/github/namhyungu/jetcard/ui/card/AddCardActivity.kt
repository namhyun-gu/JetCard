package com.github.namhyungu.jetcard.ui.card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.github.namhyungu.jetcard.ui.theme.JetCardTheme
import com.github.namhyungu.jetcard.util.extra
import dagger.hilt.android.AndroidEntryPoint

fun launchAddCardActivity(context: Context, categoryId: Int) {
    context.startActivity(createAddCardActivityIntent(context, categoryId))
}

fun createAddCardActivityIntent(context: Context, categoryId: Int): Intent {
    val intent = Intent(context, AddCardActivity::class.java)
    intent.putExtra(AddCardActivity.EXTRA_CATEGORY_ID, categoryId)
    return intent
}

@AndroidEntryPoint
class AddCardActivity : AppCompatActivity() {
    private val viewModel: AddCardViewModel by viewModels()
    private val categoryId: Int by extra(EXTRA_CATEGORY_ID, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCardTheme {
                AddCardScreen(
                    onNavigateUpClick = {
                        finish()
                    },
                    onCardAdd = { card ->
                        viewModel.addCard(categoryId, card)
                        finish()
                    }
                )
            }
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "category_id"
    }
}