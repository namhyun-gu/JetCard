package com.github.namhyungu.jetcard.data

import androidx.room.Dao
import androidx.room.Query
import com.github.namhyungu.jetcard.model.CardCategory
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CardCategoryDao : BaseDao<CardCategory> {

    @Query("SELECT * FROM card_category ORDER BY is_pinned")
    abstract fun selectAll(): Flow<List<CardCategory>>

    @Query("SELECT * FROM card_category WHERE id = :id")
    abstract suspend fun selectById(id: Int): CardCategory

}