package com.supplementtracker.ui.supplements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity
import com.supplementtracker.data.repository.SupplementRepository
import com.supplementtracker.domain.DateProvider
import com.supplementtracker.domain.SystemDateProvider
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
    private val canScheduleExact: () -> Boolean,
    private val dateProvider: DateProvider = SystemDateProvider
) : ViewModel() {

    // Mutable date state; updated by onResume() when the date has advanced past midnight.
    private val _currentDate = MutableStateFlow(dateProvider.today().toString())

    private val _state = MutableStateFlow(SupplementsUiState())
    val state: StateFlow<SupplementsUiState> = _state.asStateFlow()

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        viewModelScope.launch {
            _currentDate.flatMapLatest { date ->
                flow {
                    // Ensure today's occurrences exist before subscribing to the live flow
                    repo.ensureTodayOccurrences(date)
                    emitAll(
                        combine(
                            repo.allGroups,
                            repo.activeSupplements,
                            repo.observeTodayOccurrences(date)
                        ) { groups, supplements, occurrences ->
                            val occurrenceMap = occurrences.associateBy { it.supplementId }
                            val grouped = groups.map { group ->
                                val groupSupps = supplements.filter { it.scheduleGroupId == group.id }
                                val groupOccs = groupSupps.associate { s ->
                                    s.id to (occurrenceMap[s.id] ?: DailyOccurrenceEntity(
                                        supplementId = s.id,
                                        scheduledDate = date,
                                        scheduledHour = group.reminderHour,
                                        scheduledMinute = group.reminderMinute,
                                        scheduleGroupId = group.id
                                    ))
                                }
                                GroupWithSupplements(group, groupSupps, groupOccs)
                            }.filter { it.supplements.isNotEmpty() }

                            SupplementsUiState(
                                today = date,
                                groups = grouped,
                                totalScheduled = occurrences.size,
                                totalCompleted = occurrences.count { it.completed },
                                exactAlarmAvailable = canScheduleExact(),
                                loading = false
                            )
                        }
                    )
                }
            }.collect { _state.value = it }
        }
    }

    /** Call from the screen's ON_RESUME lifecycle event to detect midnight rollover. */
    fun onResume() {
        val newDate = dateProvider.today().toString()
        if (_currentDate.value != newDate) {
            _currentDate.value = newDate
        }
    }

    fun toggleCompletionWithContext(supplementId: Long, currentlyCompleted: Boolean, cancelSnooze: (Long, String) -> Unit) {
        viewModelScope.launch {
            val date = dateProvider.today().toString()
            repo.setCompleted(supplementId, date, !currentlyCompleted)
            if (!currentlyCompleted) {
                val groupId = _state.value.groups.find { g -> g.supplements.any { it.id == supplementId } }?.group?.id
                if (groupId != null) {
                    val outstanding = repo.getOutstandingInGroup(groupId, date)
                    if (outstanding.isEmpty()) cancelSnooze(groupId, date)
                }
            }
        }
    }

    fun saveSupplement(supplement: SupplementEntity, isNew: Boolean) {
        viewModelScope.launch {
            val savedId = repo.upsertSupplement(supplement)
            if (isNew) {
                val saved = repo.getSupplementById(savedId) ?: return@launch
                repo.createOccurrencesForNewSupplement(saved, dateProvider.today().toString())
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
