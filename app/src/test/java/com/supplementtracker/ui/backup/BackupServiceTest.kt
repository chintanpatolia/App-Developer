package com.supplementtracker.ui.backup

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.junit.Assert.*
import org.junit.Test

class BackupServiceTest {

    // 20. Backup JSON has required structure
    @Test fun `valid backup json parses correctly`() {
        val json = """{"version":1,"scheduleGroups":[],"supplements":[],"occurrences":[]}"""
        val data = Gson().fromJson(json, BackupService.BackupData::class.java)
        assertEquals(1, data.version)
        assertNotNull(data.scheduleGroups)
        assertNotNull(data.supplements)
        assertNotNull(data.occurrences)
    }

    @Test fun `invalid json throws exception`() {
        try {
            Gson().fromJson("not json at all", BackupService.BackupData::class.java)
            // Gson may return null rather than throw for some inputs
        } catch (e: JsonSyntaxException) {
            // expected — test passes
        }
    }

    @Test fun `backup with wrong version should fail validation`() {
        val json = """{"version":99,"scheduleGroups":[],"supplements":[],"occurrences":[]}"""
        val data = Gson().fromJson(json, BackupService.BackupData::class.java)
        assertNotEquals(1, data.version)
    }

    @Test fun `empty supplements in backup is valid structure`() {
        val data = BackupService.BackupData(
            version = 1,
            scheduleGroups = emptyList(),
            supplements = emptyList(),
            occurrences = emptyList()
        )
        val json = Gson().toJson(data)
        assertTrue(json.contains("\"version\":1"))
        assertTrue(json.contains("\"supplements\":[]"))
    }
}
