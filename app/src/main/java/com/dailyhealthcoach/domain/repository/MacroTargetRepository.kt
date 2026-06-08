package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.MacroTarget
import kotlinx.coroutines.flow.Flow

interface MacroTargetRepository {
    fun observeActiveTarget(): Flow<MacroTarget?>
}
