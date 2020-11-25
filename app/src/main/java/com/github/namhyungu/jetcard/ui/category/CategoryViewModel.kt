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
import com.github.namhyungu.jetcard.model.Resource
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(
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
            _cards.value = Resource(cards)
        }
    }

    fun deleteCard(categoryId: Int, card: CardContent) {
        viewModelScope.launch {
            cardContentDao.delete(card)
            getCards(categoryId)
        }
    }
}