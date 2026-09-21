package com.supplementtracker.ui.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplementtracker.data.entity.SupplementEntity
import com.supplementtracker.data.repository.SupplementRepository
import com.supplementtracker.domain.AdherenceCalculator
import com.supplementtracker.domain.AdherenceSummary
import com.supplementtracker.domain.DayStats
import com.supplementtracker.domain.SupplementAdherence
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class TrendRange(val label: String, val days: Int?) {
    DAYS_7("7D", 7),
    DAYS_30("30D", 30),
    DAYS_90("90D", 90),
    ALL("ALL", null)
}

data class TrendsUiState(
    val range: TrendRange = TrendRange.DAYS_30,
    val dayStats: List<DayStats> = emptyList(),
    val summary: AdherenceSummary? = null,
    val perSupplement: List<SupplementAdherence> = emptyList(),
    val selectedSupplementId: Long? = null,
    val selectedSupplementName: String? = null,
    val loading: Boolean = true
)

class TrendsViewModel(private val repo: SupplementRepository) : ViewModel() {

    private val _range = MutableStateFlow(TrendRange.DAYS_30)
    private val _selectedSupplementId = MutableStateFlow<Long?>(null)
    private val _state = MutableStateFlow(TrendsUiState())
    val state: StateFlow<TrendsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_range, _selectedSupplementId) { range, selectedId ->
                range to selectedId
            }.collect { (range, selectedId) ->
                loadData(range, selectedId)
            }
        }
    }

    fun setRange(range: TrendRange) { _range.value = range }
    fun selectSupplement(id: Long?) { _selectedSupplementId.value = id }

    private suspend fun loadData(range: TrendRange, selectedSupplementId: Long?) {
        val today = LocalDate.now().toString()
        val startDate = range.days?.let { LocalDate.now().minusDays(it.toLong() - 1).toString() }
            ?: repo.getEarliestDate() ?: today

        val occurrences = repo.getOccurrencesInRange(startDate, today)
        val filtered = if (selectedSupplementId != null) {
            occurrences.filter { it.supplementId == selectedSupplementId }
        } else {
            occurrences
        }

        val dayStats = AdherenceCalculator.computeDayStats(filtered)
        val summary = AdherenceCalculator.computeSummary(dayStats, today)

        val supplements = repo.getActiveSupplementsList()
        val nameMap = supplements.associate { it.id to it.name }
        val perSupplement = AdherenceCalculator.computePerSupplementAdherence(occurrences, nameMap)

        val selName = if (selectedSupplementId != null) nameMap[selectedSupplementId] else null

        _state.value = TrendsUiState(
            range = range,
            dayStats = dayStats,
            summary = summary,
            perSupplement = perSupplement,
            selectedSupplementId = selectedSupplementId,
            selectedSupplementName = selName,
            loading = false
        )
    }
}
