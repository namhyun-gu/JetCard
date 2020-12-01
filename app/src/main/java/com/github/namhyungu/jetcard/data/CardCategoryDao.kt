package com.github.namhyungu.jetcard.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.CardCategoryWithContents
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CardCategoryDao : BaseDao<CardCategory> {

    @Query("SELECT * FROM card_category ORDER BY is_pinned")
    abstract fun selectAll(): Flow<List<CardCategory>>

    @Query("SELECT * FROM card_category WHERE id = :id")
    abstract suspend fun selectById(id: Int): CardCategory

    @Transaction
    @Query("SELECT * FROM card_category WHERE id = :id")
    abstract suspend fun selectWithContents(id: Int): CardCategoryWithContents

    @Transaction
    open suspend fun delete(id: Int) {
        val category = selectById(id)
        delete(category)
    }
}