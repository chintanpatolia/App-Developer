package com.dailyhealthcoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.dailyhealthcoach.data.local.entity.MacroTargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MacroTargetDao {
    @Query("SELECT * FROM macro_targets WHERE isActive = 1 ORDER BY id DESC LIMIT 1")
    fun observeActiveTarget(): Flow<MacroTargetEntity?>

    @Query("SELECT COUNT(*) FROM macro_targets")
    suspend fun countTargets(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(target: MacroTargetEntity)

    @Query("SELECT * FROM macro_targets WHERE isActive = 1 ORDER BY id DESC LIMIT 1")
    suspend fun getActiveTarget(): MacroTargetEntity?

    @Upsert
    suspend fun upsert(target: MacroTargetEntity)
}
