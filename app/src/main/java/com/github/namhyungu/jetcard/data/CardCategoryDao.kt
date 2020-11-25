package com.github.namhyungu.jetcard.data

import androidx.room.*
import com.github.namhyungu.jetcard.model.CardCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CardCategoryDao {

    @Query("SELECT * FROM card_category ORDER BY is_pinned")
    fun selectAll(): Flow<List<CardCategory>>

    @Query("SELECT * FROM card_category WHERE id = :id")
    suspend fun selectById(id: Int): CardCategory

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(cardCategory: CardCategory)

    @Update
    suspend fun update(cardCategory: CardCategory)

    @Delete
    suspend fun delete(cardCategory: CardCategory)

}