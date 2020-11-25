package com.github.namhyungu.jetcard.data

import androidx.room.TypeConverter
import com.github.namhyungu.jetcard.model.CardStatus

class DatabaseConverters {
    @TypeConverter
    fun fromCardStatus(value: Int?): CardStatus? {
        return value?.let { CardStatus.values()[it] }
    }

    @TypeConverter
    fun cardStatusToInt(cardStatus: CardStatus?): Int? {
        return cardStatus?.ordinal
    }
}