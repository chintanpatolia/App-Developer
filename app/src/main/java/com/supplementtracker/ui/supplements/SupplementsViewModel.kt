package com.supplementtracker.ui.supplements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity
import com.supplementtracker.data.repository.SupplementRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

data class GroupWithSupplements(
    val group: ScheduleGroupEntity,
    val supplements: List<SupplementEntity>,
    val occurrences: Map<Long, DailyOccurrenceEntity>   // supplementId -> occurrence
)

data class SupplementsUiState(
    val today: String = LocalDate.now().toString(),
    val groups: List<GroupWithSupplements> = emptyList(),
    val totalScheduled: Int = 0,
    val totalCompleted: Int = 0,
    val exactAlarmAvailable: Boolean = true,
    val loading: Boolean = true
)

class SupplementsViewModel(
    private val repo: SupplementRepository,
    private val alarmScheduler: (ScheduleGroupEntity) -> Unit,
    private val cancelAlarm: (Long) -> Unit,
    private val rescheduleAll: () -> Unit,
    private val canScheduleExact: () -> Boolean
) : ViewModel() {

    private val today = LocalDate.now().toString()

    private val _state = MutableStateFlow(SupplementsUiState())
    val state: StateFlow<SupplementsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // Ensure today's occurrences exist
            repo.ensureTodayOccurrences(today)
        }

        // Combine groups, supplements, occurrences
        @OptIn(ExperimentalCoroutinesApi::class)
        viewModelScope.launch {
            combine(
                repo.allGroups,
                repo.activeSupplements,
                repo.observeTodayOccurrences(today)
            ) { groups, supplements, occurrences ->
                val occurrenceMap = occurrences.associateBy { it.supplementId }
                val grouped = groups.map { group ->
                    val groupSupps = supplements.filter { it.scheduleGroupId == group.id }
                    val groupOccs = groupSupps.associate { it.id to (occurrenceMap[it.id] ?: DailyOccurrenceEntity(supplementId = it.id, scheduledDate = today, scheduledHour = group.reminderHour, scheduledMinute = group.reminderMinute, scheduleGroupId = group.id)) }
                    GroupWithSupplements(group, groupSupps, groupOccs)
                }.filter { it.supplements.isNotEmpty() }

                val totalScheduled = occurrences.size
                val totalCompleted = occurrences.count { it.completed }

                SupplementsUiState(
                    today = today,
                    groups = grouped,
                    totalScheduled = totalScheduled,
                    totalCompleted = totalCompleted,
                    exactAlarmAvailable = canScheduleExact(),
                    loading = false
                )
            }.collect { _state.value = it }
        }
    }



    fun toggleCompletionWithContext(supplementId: Long, currentlyCompleted: Boolean, cancelSnooze: (Long, String) -> Unit) {
        viewModelScope.launch {
            repo.setCompleted(supplementId, today, !currentlyCompleted)
            if (!currentlyCompleted) { // toggling TO completed
                val groupId = _state.value.groups.find { g -> g.supplements.any { it.id == supplementId } }?.group?.id
                if (groupId != null) {
                    val outstanding = repo.getOutstandingInGroup(groupId, today)
                    if (outstanding.isEmpty()) cancelSnooze(groupId, today)
                }
            }
        }
    }

    fun saveSupplement(supplement: SupplementEntity, isNew: Boolean) {
        viewModelScope.launch {
            val savedId = repo.upsertSupplement(supplement)
            if (isNew) {
                val saved = repo.getSupplementById(savedId) ?: return@launch
                repo.createOccurrencesForNewSupplement(saved, today)
            }
            alarmScheduler(repo.getGroupById(supplement.scheduleGroupId) ?: return@launch)
        }
    }

    fun updateGroup(group: ScheduleGroupEntity) {
        viewModelScope.launch {
            cancelAlarm(group.id)
            repo.upsertGroup(group)
            alarmScheduler(group)
        }
    }

    fun deleteSupplement(supplement: SupplementEntity) {
        viewModelScope.launch {
            repo.deleteSupplement(supplement)
        }
    }

    fun getGroups() = repo.allGroups
}
