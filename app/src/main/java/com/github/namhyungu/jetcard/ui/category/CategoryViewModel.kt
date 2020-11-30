package com.github.namhyungu.jetcard.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.namhyungu.jetcard.data.CardCategoryDao
import com.github.namhyungu.jetcard.data.CardContentDao
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.CardContent
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(
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
            _cards.value = cards
        }
    }

    fun deleteCard(categoryId: Int, card: CardContent) {
        viewModelScope.launch {
            cardContentDao.delete(card)
            getCards(categoryId)
        }
    }
}