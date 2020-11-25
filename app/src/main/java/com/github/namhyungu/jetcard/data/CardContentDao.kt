package com.github.namhyungu.jetcard.data

import androidx.room.*
import com.github.namhyungu.jetcard.model.CardContent

@Dao
interface CardContentDao {

    @Query("SELECT * FROM card WHERE category_id = :categoryId")
    suspend fun selectByCategoryId(categoryId: Int): List<CardContent>

    @Query("SELECT * FROM card WHERE id = :cardId")
    suspend fun selectById(cardId: Int): CardContent

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(cardContent: CardContent)

    @Update
    suspend fun update(cardContent: CardContent)

    @Delete
    suspend fun delete(cardContent: CardContent)

}