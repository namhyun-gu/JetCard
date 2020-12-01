package com.github.namhyungu.jetcard.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.namhyungu.jetcard.data.CardCategoryDao
import com.github.namhyungu.jetcard.data.CardContentDao
import com.github.namhyungu.jetcard.model.CardCategoryWithContents
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(
    private val categoryDao: CardCategoryDao,
    private val cardContentDao: CardContentDao
) : ViewModel() {
    private val _categoryWithContents = MutableLiveData<CardCategoryWithContents>()
    val categoryWithContents: LiveData<CardCategoryWithContents> = _categoryWithContents

    fun getCategoryWithContents(categoryId: Int) {
        viewModelScope.launch {
            val categoryWithContents = categoryDao.selectWithContents(categoryId)
            _categoryWithContents.value = categoryWithContents
        }
    }

    fun deleteCard(categoryId: Int, cardId: Int) {
        viewModelScope.launch {
            cardContentDao.delete(cardId)
            getCategoryWithContents(categoryId)
        }
    }

    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            categoryDao.delete(categoryId)
        }
    }
}