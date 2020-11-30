package com.github.namhyungu.jetcard.data

import androidx.room.Dao
import androidx.room.Query
import com.github.namhyungu.jetcard.model.CardContent

@Dao
abstract class CardContentDao : BaseDao<CardContent> {

    @Query("SELECT * FROM card WHERE category_id = :categoryId")
    abstract suspend fun selectByCategoryId(categoryId: Int): List<CardContent>

    @Query("SELECT * FROM card WHERE id = :cardId")
    abstract suspend fun selectById(cardId: Int): CardContent

}