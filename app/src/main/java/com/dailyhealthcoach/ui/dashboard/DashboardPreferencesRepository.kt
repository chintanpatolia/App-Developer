package com.dailyhealthcoach.ui.dashboard

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class DashboardPreferencesRepository(context: Context) {
    private val prefs = context.getSharedPreferences("dashboard_prefs", Context.MODE_PRIVATE)

    fun loadLayout(): List<DashboardCardConfig> {
        val json = prefs.getString(LAYOUT_KEY, null) ?: return DEFAULT_LAYOUT
        return try {
            val array = JSONArray(json)
            val loaded = (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                DashboardCardConfig(
                    key = DashboardCardKey.valueOf(obj.getString("key")),
                    visible = obj.getBoolean("visible"),
                    order = obj.getInt("order")
                )
            }
            val loadedKeys = loaded.map { it.key }.toSet()
            val missing = DEFAULT_LAYOUT
                .filter { it.key !in loadedKeys }
                .mapIndexed { i, c -> c.copy(order = loaded.size + i) }
            (loaded + missing).sortedBy { it.order }
        } catch (e: Exception) {
            DEFAULT_LAYOUT
        }
    }

    fun saveLayout(configs: List<DashboardCardConfig>) {
        val array = JSONArray()
        configs.forEach { config ->
            array.put(JSONObject().apply {
                put("key", config.key.name)
                put("visible", config.visible)
                put("order", config.order)
            })
        }
        prefs.edit().putString(LAYOUT_KEY, array.toString()).apply()
    }

    fun resetLayout() {
        prefs.edit().remove(LAYOUT_KEY).apply()
    }

    companion object {
        private const val LAYOUT_KEY = "dashboard_layout_v1"

        val DEFAULT_LAYOUT: List<DashboardCardConfig> = listOf(
            DashboardCardConfig(DashboardCardKey.HEALTH_SCORE, visible = true, order = 0),
            DashboardCardConfig(DashboardCardKey.RECOVERY, visible = true, order = 1),
            DashboardCardConfig(DashboardCardKey.WORKOUT, visible = true, order = 2),
            DashboardCardConfig(DashboardCardKey.NUTRITION, visible = true, order = 3),
            DashboardCardConfig(DashboardCardKey.HABITS, visible = true, order = 4),
            DashboardCardConfig(DashboardCardKey.STEPS, visible = true, order = 5),
            DashboardCardConfig(DashboardCardKey.SLEEP, visible = true, order = 6),
            DashboardCardConfig(DashboardCardKey.WEIGHT, visible = true, order = 7),
            DashboardCardConfig(DashboardCardKey.BODY_FAT, visible = true, order = 8),
            DashboardCardConfig(DashboardCardKey.TOMORROW, visible = true, order = 9),
            DashboardCardConfig(DashboardCardKey.VIEW_PROGRESS, visible = true, order = 10),
        )
    }
}
