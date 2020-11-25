package com.github.namhyungu.jetcard.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.github.namhyungu.jetcard.data.CardCategoryDao
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val cardCategoryDao: CardCategoryDao
) : ViewModel() {
    val cardCategories = liveData {
        emit(Resource(loading = true))
        cardCategoryDao.selectAll().collect { categories ->
            emit(Resource(data = categories))
        }
    }

    fun updateCategoryPin(category: CardCategory) {
        viewModelScope.launch {
            cardCategoryDao.update(category.copy(isPinned = !category.isPinned))
        }
    }
}