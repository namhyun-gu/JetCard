package com.github.namhyungu.jetcard.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.namhyungu.jetcard.model.CardCategory
import com.github.namhyungu.jetcard.model.CardContent

@Database(entities = [CardCategory::class, CardContent::class], version = 1, exportSchema = true)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardCategoryDao(): CardCategoryDao

    abstract fun cardContentDao(): CardContentDao

}