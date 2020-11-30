package com.github.namhyungu.jetcard.model

import androidx.room.Embedded
import androidx.room.Relation

data class CardCategoryWithContents(
    @Embedded val category: CardCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val contents: List<CardContent>
)