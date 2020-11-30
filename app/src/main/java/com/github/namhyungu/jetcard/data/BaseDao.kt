package com.github.namhyungu.jetcard.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)

}