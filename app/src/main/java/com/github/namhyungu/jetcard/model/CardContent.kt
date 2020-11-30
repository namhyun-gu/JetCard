package com.github.namhyungu.jetcard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "card",
    foreignKeys = [
        ForeignKey(
            entity = CardCategory::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CardContent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "category_id") val categoryId: Int = 0,
    val question: String,
    val answer: String,
    val status: CardStatus = CardStatus.None
)

enum class CardStatus {
    Incomplete,
    Complete,
    None
}