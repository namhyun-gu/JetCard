package com.github.namhyungu.jetcard.ui.card

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.namhyungu.jetcard.data.CardCategoryDao
import com.github.namhyungu.jetcard.data.CardContentDao
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.CardContent
import com.github.namhyungu.jetcard.model.CardStatus
import kotlinx.coroutines.launch

class CardViewModel @ViewModelInject constructor(
    private val categoryDao: CardCategoryDao,
    private val cardContentDao: CardContentDao
) : ViewModel() {
    private val _category = MutableLiveData<CardCategory>()
    val category: LiveData<CardCategory> = _category

    private val _cards = MutableLiveData<List<CardContent>>()
    val cards: LiveData<List<CardContent>> = _cards

    fun getCategory(categoryId: Int) {
        viewModelScope.launch {
            val category = categoryDao.selectById(categoryId)
            _category.value = category
        }
    }

    fun getCards(categoryId: Int) {
        viewModelScope.launch {
            val cards = cardContentDao
                .selectByCategoryId(categoryId)
                .shuffled()
            _cards.value = cards
        }
    }

    fun updateCard(cardId: Int, action: String) {
        viewModelScope.launch {
            val card = cardContentDao.selectById(cardId)
            val updatedCard = card.copy(
                status = when (action) {
                    CardActions.actionComplete -> CardStatus.Complete
                    CardActions.actionIncomplete -> CardStatus.Incomplete
                    else -> card.status
                }
            )
            cardContentDao.update(updatedCard)
        }
    }
}