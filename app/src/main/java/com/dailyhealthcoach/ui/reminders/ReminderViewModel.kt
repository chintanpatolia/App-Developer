package com.dailyhealthcoach.ui.reminders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dailyhealthcoach.notifications.ReminderPreferences
import com.dailyhealthcoach.notifications.ReminderScheduler
import com.dailyhealthcoach.notifications.ReminderType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ReminderItemUiState(
    val type: ReminderType,
    val label: String,
    val body: String,
    val enabled: Boolean,
    val hour: Int,
    val minute: Int
) {
    val timeLabel: String get() = String.format("%02d:%02d", hour, minute)
}

data class ReminderUiState(
    val items: List<ReminderItemUiState> = emptyList()
)

class ReminderViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = ReminderPreferences(app)
    private val _uiState = MutableStateFlow(buildState())
    val uiState: StateFlow<ReminderUiState> = _uiState.asStateFlow()

    fun toggle(type: ReminderType, enabled: Boolean) {
        prefs.setEnabled(type, enabled)
        val item = _uiState.value.items.first { it.type == type }
        if (enabled) {
            ReminderScheduler.schedule(getApplication(), type, item.hour, item.minute)
        } else {
            ReminderScheduler.cancel(getApplication(), type)
        }
        _uiState.value = buildState()
    }

    fun updateTime(type: ReminderType, hour: Int, minute: Int) {
        prefs.setTime(type, hour, minute)
        if (prefs.isEnabled(type)) {
            ReminderScheduler.schedule(getApplication(), type, hour, minute)
        }
        _uiState.value = buildState()
    }

    private fun buildState() = ReminderUiState(
        items = ReminderType.entries.map { t ->
            ReminderItemUiState(
                type = t,
                label = t.title,
                body = t.body,
                enabled = prefs.isEnabled(t),
                hour = prefs.getHour(t),
                minute = prefs.getMinute(t)
            )
        }
    )
}

class ReminderViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
