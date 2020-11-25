package com.github.namhyungu.jetcard.ui.card

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.namhyungu.jetcard.data.CardContentDao
import com.github.namhyungu.jetcard.model.CardContent
import kotlinx.coroutines.launch

class AddCardViewModel @ViewModelInject constructor(
    private val cardContentDao: CardContentDao
) : ViewModel() {

    fun addCard(categoryId: Int, card: CardContent) {
        viewModelScope.launch {
            cardContentDao.insert(card.copy(categoryId = categoryId))
        }
    }
}