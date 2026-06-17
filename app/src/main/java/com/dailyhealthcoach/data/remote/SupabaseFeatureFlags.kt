package com.dailyhealthcoach.data.remote

import com.dailyhealthcoach.BuildConfig

object SupabaseFeatureFlags {
    val isEnabled: Boolean =
        BuildConfig.SUPABASE_ENABLED &&
        BuildConfig.SUPABASE_URL.isNotBlank() &&
        BuildConfig.SUPABASE_ANON_KEY.isNotBlank()
}
