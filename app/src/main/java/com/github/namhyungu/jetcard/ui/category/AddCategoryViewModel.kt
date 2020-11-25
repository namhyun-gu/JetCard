package com.github.namhyungu.jetcard.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.namhyungu.jetcard.data.CardCategoryDao
import com.github.namhyungu.jetcard.model.CardCategory
import kotlinx.coroutines.launch

class AddCategoryViewModel @ViewModelInject constructor(
    private val categoryDao: CardCategoryDao
) : ViewModel() {

    fun addCategory(cardCategory: CardCategory) {
        viewModelScope.launch {
            categoryDao.insert(cardCategory)
        }
    }
}