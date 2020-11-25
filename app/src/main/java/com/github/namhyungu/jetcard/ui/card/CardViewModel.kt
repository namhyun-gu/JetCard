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
import com.github.namhyungu.jetcard.model.Resource
import kotlinx.coroutines.launch

class CardViewModel @ViewModelInject constructor(
    private val categoryDao: CardCategoryDao,
    private val cardContentDao: CardContentDao
) : ViewModel() {
    private val _category = MutableLiveData<Resource<CardCategory>>()
    val category: LiveData<Resource<CardCategory>> = _category

    private val _cards = MutableLiveData<Resource<List<CardContent>>>()
    val cards: LiveData<Resource<List<CardContent>>> = _cards

    fun getCategory(categoryId: Int) {
        viewModelScope.launch {
            _category.value = Resource(loading = true)
            val category = categoryDao.selectById(categoryId)
            _category.value = Resource(category)
        }
    }

    fun getCards(categoryId: Int) {
        viewModelScope.launch {
            _cards.value = Resource(loading = true)
            val cards = cardContentDao
                .selectByCategoryId(categoryId)
                .shuffled()
            _cards.value = Resource(cards)
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