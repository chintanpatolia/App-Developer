package com.dailyhealthcoach.data.export

import android.content.Context
import android.net.Uri
import com.dailyhealthcoach.data.local.AppDatabase
import com.dailyhealthcoach.data.local.entity.BodyMetricLogEntity
import com.dailyhealthcoach.data.local.entity.DailyHabitLogEntity
import com.dailyhealthcoach.data.local.entity.DailyRecommendationEntity
import com.dailyhealthcoach.data.local.entity.FoodEntryEntity
import com.dailyhealthcoach.data.local.entity.HabitDefinitionEntity
import com.dailyhealthcoach.data.local.entity.MacroTargetEntity
import com.dailyhealthcoach.data.local.entity.RecoveryActivityEntity
import com.dailyhealthcoach.data.local.entity.RecoveryScoreEntity
import com.dailyhealthcoach.data.local.entity.UserProfileEntity
import com.dailyhealthcoach.data.local.entity.WorkoutEntity
import com.dailyhealthcoach.data.local.entity.WorkoutExerciseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.Instant

class DataRestoreService(private val db: AppDatabase) {

    sealed class RestoreResult {
        object Success : RestoreResult()
        data class Invalid(val reason: String) : RestoreResult()
        data class Error(val message: String) : RestoreResult()
    }

    suspend fun validate(context: Context, uri: Uri): RestoreResult = withContext(Dispatchers.IO) {
        try {
            val text = context.contentResolver.openInputStream(uri)
                ?.bufferedReader()?.readText()
                ?: return@withContext RestoreResult.Invalid("Could not read the selected file.")
            if (text.isBlank()) return@withContext RestoreResult.Invalid("The selected file is empty.")
            val json = try {
                JSONObject(text)
            } catch (e: Exception) {
                return@withContext RestoreResult.Invalid("This does not appear to be a valid backup file.")
            }
            if (json.optString("appName") != "Daily Health Coach") {
                return@withContext RestoreResult.Invalid("This does not appear to be a valid Daily Health Coach backup file.")
            }
            if (json.optInt("schemaVersion", -1) < 1) {
                return@withContext RestoreResult.Invalid("Backup file is missing schema version information.")
            }
            RestoreResult.Success
        } catch (e: Exception) {
            RestoreResult.Invalid("This does not appear to be a valid backup file.")
        }
    }

    suspend fun restore(context: Context, uri: Uri): RestoreResult = withContext(Dispatchers.IO) {
        try {
            val text = context.contentResolver.openInputStream(uri)
                ?.bufferedReader()?.readText()
                ?: return@withContext RestoreResult.Error("Could not read the selected file.")
            val json = JSONObject(text)
            restoreAll(json)
            RestoreResult.Success
        } catch (e: Exception) {
            RestoreResult.Error("Restore failed: ${e.message ?: "Unknown error"}")
        }
    }

    private suspend fun restoreAll(json: JSONObject) {
        val now = Instant.now().toString()

        json.optJSONObject("userProfile")?.let { p ->
            db.userProfileDao().upsert(
                UserProfileEntity(
                    id = p.optLong("id", 1),
                    name = p.optString("name", ""),
                    age = p.nullableInt("age"),
                    sex = p.nullableString("sex"),
                    heightInches = p.nullableDouble("heightInches"),
                    birthDate = p.nullableString("birthDate"),
                    bedtime = p.nullableString("bedtime"),
                    weightGoalPounds = p.nullableDouble("weightGoalPounds"),
                    bodyFatGoalPercent = p.nullableDouble("bodyFatGoalPercent"),
                    stepMinTarget = p.nullableInt("stepMinTarget"),
                    stepMaxTarget = p.nullableInt("stepMaxTarget"),
                    sleepTargetHours = p.nullableDouble("sleepTargetHours"),
                    strengthTrainingDaysPerWeek = p.nullableInt("strengthTrainingDaysPerWeek"),
                    createdAt = p.optString("createdAt", now),
                    updatedAt = p.optString("updatedAt", now)
                )
            )
        }

        json.optJSONObject("activeMacroTarget")?.let { t ->
            db.macroTargetDao().upsert(
                MacroTargetEntity(
                    id = t.optLong("id", 0),
                    proteinMinGrams = t.optInt("proteinMinGrams", 170),
                    proteinMaxGrams = t.optInt("proteinMaxGrams", 200),
                    calorieTarget = t.nullableInt("calorieTarget"),
                    carbTargetGrams = t.nullableInt("carbTargetGrams"),
                    fatTargetGrams = t.nullableInt("fatTargetGrams"),
                    fiberTargetGrams = t.nullableInt("fiberTargetGrams"),
                    isActive = true,
                    createdAt = t.optString("createdAt", now),
                    updatedAt = t.optString("updatedAt", now)
                )
            )
        }

        json.optJSONArray("habitDefinitions")?.let { arr ->
            val habits = (0 until arr.length()).map { i ->
                val h = arr.getJSONObject(i)
                HabitDefinitionEntity(
                    id = h.optLong("id", 0),
                    name = h.optString("name"),
                    description = h.optString("description"),
                    frequencyType = h.optString("frequencyType", "DAILY"),
                    targetPerWeek = h.nullableInt("targetPerWeek"),
                    reminderWindow = h.nullableString("reminderWindow"),
                    sortOrder = h.optInt("sortOrder", 0),
                    isActive = h.optBoolean("isActive", true)
                )
            }
            db.habitDefinitionDao().upsertAll(habits)
        }

        json.optJSONArray("habitLogs")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                val l = arr.getJSONObject(i)
                db.dailyHabitLogDao().upsert(
                    DailyHabitLogEntity(
                        id = l.optLong("id", 0),
                        habitDefinitionId = l.optLong("habitDefinitionId"),
                        date = l.optString("date"),
                        status = l.optString("status", "SKIPPED"),
                        notes = l.nullableString("notes"),
                        updatedAt = l.optString("updatedAt", now)
                    )
                )
            }
        }

        json.optJSONArray("foodEntries")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                val f = arr.getJSONObject(i)
                db.foodEntryDao().upsert(
                    FoodEntryEntity(
                        id = f.optLong("id", 0),
                        date = f.optString("date"),
                        mealName = f.optString("mealName", "Snack"),
                        foodName = f.optString("foodName"),
                        brandName = f.nullableString("brandName"),
                        barcode = f.nullableString("barcode"),
                        servingDescription = f.nullableString("servingDescription"),
                        calories = f.nullableInt("calories"),
                        proteinGrams = f.nullableDouble("proteinGrams"),
                        carbGrams = f.nullableDouble("carbGrams"),
                        fatGrams = f.nullableDouble("fatGrams"),
                        fiberGrams = f.nullableDouble("fiberGrams"),
                        mealTime = f.nullableString("mealTime"),
                        isWholeFoodBased = f.optBoolean("isWholeFoodBased", false),
                        isProcessed = f.optBoolean("isProcessed", false),
                        isFermented = f.optBoolean("isFermented", false),
                        source = f.optString("source", "MANUAL"),
                        notes = f.nullableString("notes"),
                        isSaved = f.optBoolean("isSaved", false),
                        createdAt = f.optString("createdAt", now),
                        updatedAt = f.optString("updatedAt", now)
                    )
                )
            }
        }

        json.optJSONArray("bodyMetricLogs")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                val b = arr.getJSONObject(i)
                db.bodyMetricLogDao().upsert(
                    BodyMetricLogEntity(
                        id = b.optLong("id", 0),
                        date = b.optString("date"),
                        heightInches = b.nullableDouble("heightInches"),
                        bodyWeight = b.nullableDouble("bodyWeight"),
                        bodyFatPercentage = b.nullableDouble("bodyFatPercentage"),
                        calculatedBodyFatPercent = b.nullableDouble("calculatedBodyFatPercent"),
                        manualBodyFatPercent = b.nullableDouble("manualBodyFatPercent"),
                        isBodyFatOverridden = b.optBoolean("isBodyFatOverridden", false),
                        waistMeasurement = b.nullableDouble("waistMeasurement"),
                        neckMeasurement = b.nullableDouble("neckMeasurement"),
                        chestMeasurement = b.nullableDouble("chestMeasurement"),
                        armMeasurement = b.nullableDouble("armMeasurement"),
                        sleepHours = b.nullableDouble("sleepHours"),
                        energyLevel = b.nullableInt("energyLevel"),
                        stressLevel = b.nullableInt("stressLevel"),
                        sorenessLevel = b.nullableInt("sorenessLevel"),
                        restingHeartRate = b.nullableInt("restingHeartRate"),
                        stepCount = b.nullableInt("stepCount"),
                        notes = b.nullableString("notes"),
                        createdAt = b.optString("createdAt", now),
                        updatedAt = b.optString("updatedAt", now)
                    )
                )
            }
        }

        // Workouts must be upserted before workout exercises (FK dependency)
        json.optJSONArray("workouts")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                val w = arr.getJSONObject(i)
                db.workoutDao().upsert(
                    WorkoutEntity(
                        id = w.optLong("id", 0),
                        date = w.optString("date"),
                        name = w.optString("name"),
                        durationMinutes = w.nullableInt("durationMinutes"),
                        status = w.optString("status", "COMPLETED"),
                        overallRpe = w.nullableInt("overallRpe"),
                        notes = w.nullableString("notes"),
                        createdAt = w.optString("createdAt", now),
                        updatedAt = w.optString("updatedAt", now)
                    )
                )
            }
        }

        json.optJSONArray("workoutExercises")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                runCatching {
                    val e = arr.getJSONObject(i)
                    db.workoutExerciseDao().upsert(
                        WorkoutExerciseEntity(
                            id = e.optLong("id", 0),
                            workoutId = e.optLong("workoutId"),
                            exerciseId = e.optLong("exerciseId"),
                            setNumber = e.optInt("setNumber", 1),
                            reps = e.nullableInt("reps"),
                            weight = e.nullableDouble("weight"),
                            rpe = e.nullableInt("rpe"),
                            restSeconds = e.nullableInt("restSeconds"),
                            notes = e.nullableString("notes")
                        )
                    )
                }
            }
        }

        json.optJSONArray("recoveryScores")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                val r = arr.getJSONObject(i)
                db.recoveryScoreDao().upsert(
                    RecoveryScoreEntity(
                        id = r.optLong("id", 0),
                        date = r.optString("date"),
                        score = r.optInt("score", 0),
                        label = r.optString("label", ""),
                        reasonText = r.nullableString("reasonText"),
                        sleepContribution = r.optInt("sleepContribution", 0),
                        proteinContribution = r.optInt("proteinContribution", 0),
                        sorenessContribution = r.optInt("sorenessContribution", 0),
                        stressContribution = r.optInt("stressContribution", 0),
                        workoutContribution = r.optInt("workoutContribution", 0),
                        stepsContribution = r.optInt("stepsContribution", 0),
                        restDayContribution = r.optInt("restDayContribution", 0),
                        notes = r.nullableString("notes"),
                        createdAt = r.optString("createdAt", now),
                        updatedAt = r.optString("updatedAt", now)
                    )
                )
            }
        }

        json.optJSONArray("dailyRecommendations")?.let { arr ->
            (0 until arr.length()).forEach { i ->
                val rec = arr.getJSONObject(i)
                db.dailyRecommendationDao().upsert(
                    DailyRecommendationEntity(
                        id = rec.optLong("id", 0),
                        date = rec.optString("date"),
                        recommendationType = rec.optString("recommendationType"),
                        title = rec.optString("title", ""),
                        explanation = rec.optString("explanation", ""),
                        suggestedFocus = rec.optString("suggestedFocus", ""),
                        reasonBullets = rec.nullableString("reasonBullets"),
                        targetMuscleGroups = rec.nullableString("targetMuscleGroups"),
                        intensity = rec.nullableString("intensity"),
                        loadGuidance = rec.nullableString("loadGuidance"),
                        reasonSummary = rec.optString("reasonSummary", ""),
                        createdAt = rec.optString("createdAt", now),
                        updatedAt = rec.optString("updatedAt", now)
                    )
                )
            }
        }

        json.optJSONArray("recoveryActivities")?.let { arr ->
            val activities = (0 until arr.length()).map { i ->
                val a = arr.getJSONObject(i)
                RecoveryActivityEntity(
                    id = a.optLong("id", 0),
                    workoutId = a.optLong("workoutId"),
                    activityName = a.optString("activityName"),
                    status = a.optString("status", "COMPLETED"),
                    durationSeconds = a.nullableInt("durationSeconds") ?: a.nullableInt("durationMinutes")?.let { it * 60 },
                    durationMinutes = a.nullableInt("durationMinutes"),
                    rpe = a.nullableInt("rpe"),
                    notes = a.nullableString("notes")
                )
            }
            db.recoveryActivityDao().upsertAll(activities)
        }
    }

    private fun JSONObject.nullableInt(key: String): Int? =
        if (isNull(key) || !has(key)) null else optInt(key)

    private fun JSONObject.nullableDouble(key: String): Double? =
        if (isNull(key) || !has(key)) null else optDouble(key).takeUnless { it.isNaN() }

    private fun JSONObject.nullableString(key: String): String? =
        if (isNull(key) || !has(key)) null else optString(key).ifEmpty { null }
}
