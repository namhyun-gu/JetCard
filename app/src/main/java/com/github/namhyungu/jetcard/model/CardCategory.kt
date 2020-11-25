package com.github.namhyungu.jetcard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_category")
data class CardCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String = "",
    @ColumnInfo(name = "is_pinned") val isPinned: Boolean = false
)