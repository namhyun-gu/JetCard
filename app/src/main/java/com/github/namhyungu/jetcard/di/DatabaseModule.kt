package com.github.namhyungu.jetcard.di

import android.content.Context
import androidx.room.Room
import com.github.namhyungu.jetcard.data.AppDatabase
import com.github.namhyungu.jetcard.data.CardCategoryDao
import com.github.namhyungu.jetcard.data.CardContentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "JetCard"
        ).build()
    }

    @Provides
    fun provideCardCategoryDao(appDatabase: AppDatabase): CardCategoryDao {
        return appDatabase.cardCategoryDao()
    }

    @Provides
    fun provideCardContentDao(appDatabase: AppDatabase): CardContentDao {
        return appDatabase.cardContentDao()
    }
}