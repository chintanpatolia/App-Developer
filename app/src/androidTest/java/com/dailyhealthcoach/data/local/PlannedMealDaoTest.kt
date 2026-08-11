package com.dailyhealthcoach.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dailyhealthcoach.data.local.entity.PlannedMealEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Validates the planned_meals schema (version 15) and DAO behaviour.
 *
 * This test creates a fresh in-memory DB at version 15 — it does NOT replay the
 * MIGRATION_14_15 SQL path. True migration-path testing requires exportSchema = true
 * and MigrationTestHelper, which cannot be used while exportSchema = false. A real-device
 * upgrade from v14 is the complementary manual verification step.
 */
@RunWith(AndroidJUnit4::class)
class PlannedMealDaoTest {

    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndQuery_returnsCorrectRow() = runBlocking {
        db.plannedMealDao().insertAll(listOf(breakfast()))

        val rows = db.plannedMealDao().observeForDate("2026-06-21").first()

        assertEquals(1, rows.size)
        assertEquals("breakfast", rows[0].slotKey)
        assertEquals("Oatmeal Bowl", rows[0].recipeName)
    }

    @Test
    fun duplicateSlotKey_replacesExistingRow() = runBlocking {
        db.plannedMealDao().insertAll(listOf(breakfast()))
        db.plannedMealDao().insertAll(listOf(breakfast().copy(recipeName = "Pancakes", recipeId = "r2")))

        val rows = db.plannedMealDao().observeForDate("2026-06-21").first()

        assertEquals(1, rows.size)
        assertEquals("Pancakes", rows[0].recipeName)
    }

    @Test
    fun deleteForDates_removesOnlyTargetDate() = runBlocking {
        db.plannedMealDao().insertAll(listOf(
            breakfast(),
            breakfast().copy(date = "2026-06-22", recipeId = "r3")
        ))

        db.plannedMealDao().deleteForDates(listOf("2026-06-21"))

        val deleted = db.plannedMealDao().observeForDate("2026-06-21").first()
        val kept = db.plannedMealDao().observeForDate("2026-06-22").first()

        assertEquals(0, deleted.size)
        assertEquals(1, kept.size)
    }

    @Test
    fun differentSlotKeys_sameDate_storedIndependently() = runBlocking {
        db.plannedMealDao().insertAll(listOf(
            breakfast(),
            PlannedMealEntity(
                date = "2026-06-21", slotKey = "lunch",
                recipeId = "r4", recipeName = "Salad",
                calories = 300, proteinGrams = 20.0,
                carbGrams = 25.0, fatGrams = 10.0, fiberGrams = 5.0
            )
        ))

        val rows = db.plannedMealDao().observeForDate("2026-06-21").first()
        assertEquals(2, rows.size)
    }

    private fun breakfast() = PlannedMealEntity(
        date = "2026-06-21",
        slotKey = "breakfast",
        recipeId = "r1",
        recipeName = "Oatmeal Bowl",
        calories = 350,
        proteinGrams = 12.0,
        carbGrams = 60.0,
        fatGrams = 5.0,
        fiberGrams = 6.0
    )
}
