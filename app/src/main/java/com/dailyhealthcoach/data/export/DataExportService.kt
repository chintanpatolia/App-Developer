package com.dailyhealthcoach.data.export

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.dailyhealthcoach.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.time.Instant

class DataExportService(private val db: AppDatabase) {

    suspend fun exportJson(context: Context): Uri = withContext(Dispatchers.IO) {
        val json = buildJson()
        val dir = File(context.cacheDir, "exports").also { it.mkdirs() }
        val file = File(dir, "daily_health_coach_backup.json")
        file.writeText(json.toString(2))
        FileProvider.getUriForFile(context, "com.dailyhealthcoach.fileprovider", file)
    }

    private suspend fun buildJson(): JSONObject {
        val root = JSONObject()
        root.put("exportedAt", Instant.now().toString())
        root.put("appName", "Daily Health Coach")
        root.put("schemaVersion", 7)

        // User profile
        db.userProfileDao().getProfile()?.let { p ->
            root.put("userProfile", JSONObject().apply {
                put("id", p.id)
                put("name", p.name)
                put("age", p.age ?: JSONObject.NULL)
                put("sex", p.sex ?: JSONObject.NULL)
                put("heightInches", p.heightInches ?: JSONObject.NULL)
                put("birthDate", p.birthDate ?: JSONObject.NULL)
                put("bedtime", p.bedtime ?: JSONObject.NULL)
                put("weightGoalPounds", p.weightGoalPounds ?: JSONObject.NULL)
                put("bodyFatGoalPercent", p.bodyFatGoalPercent ?: JSONObject.NULL)
                put("stepMinTarget", p.stepMinTarget ?: JSONObject.NULL)
                put("stepMaxTarget", p.stepMaxTarget ?: JSONObject.NULL)
                put("sleepTargetHours", p.sleepTargetHours ?: JSONObject.NULL)
                put("strengthTrainingDaysPerWeek", p.strengthTrainingDaysPerWeek ?: JSONObject.NULL)
                put("createdAt", p.createdAt)
                put("updatedAt", p.updatedAt)
            })
        }

        // Active macro target
        db.macroTargetDao().getActiveTarget()?.let { t ->
            root.put("activeMacroTarget", JSONObject().apply {
                put("id", t.id)
                put("proteinMinGrams", t.proteinMinGrams)
                put("proteinMaxGrams", t.proteinMaxGrams)
                put("calorieTarget", t.calorieTarget ?: JSONObject.NULL)
                put("carbTargetGrams", t.carbTargetGrams ?: JSONObject.NULL)
                put("fatTargetGrams", t.fatTargetGrams ?: JSONObject.NULL)
                put("fiberTargetGrams", t.fiberTargetGrams ?: JSONObject.NULL)
                put("createdAt", t.createdAt)
                put("updatedAt", t.updatedAt)
            })
        }

        // Habit definitions
        root.put("habitDefinitions", JSONArray().also { arr ->
            db.habitDefinitionDao().getAll().forEach { h ->
                arr.put(JSONObject().apply {
                    put("id", h.id)
                    put("name", h.name)
                    put("description", h.description)
                    put("frequencyType", h.frequencyType)
                    put("targetPerWeek", h.targetPerWeek ?: JSONObject.NULL)
                    put("reminderWindow", h.reminderWindow ?: JSONObject.NULL)
                    put("sortOrder", h.sortOrder)
                    put("isActive", h.isActive)
                })
            }
        })

        // Daily habit logs
        root.put("habitLogs", JSONArray().also { arr ->
            db.dailyHabitLogDao().getAll().forEach { l ->
                arr.put(JSONObject().apply {
                    put("id", l.id)
                    put("habitDefinitionId", l.habitDefinitionId)
                    put("date", l.date)
                    put("status", l.status)
                    put("notes", l.notes ?: JSONObject.NULL)
                    put("updatedAt", l.updatedAt)
                })
            }
        })

        // Food entries
        root.put("foodEntries", JSONArray().also { arr ->
            db.foodEntryDao().observeAll().first().forEach { f ->
                arr.put(JSONObject().apply {
                    put("id", f.id)
                    put("date", f.date)
                    put("mealName", f.mealName)
                    put("foodName", f.foodName)
                    put("brandName", f.brandName ?: JSONObject.NULL)
                    put("barcode", f.barcode ?: JSONObject.NULL)
                    put("servingDescription", f.servingDescription ?: JSONObject.NULL)
                    put("calories", f.calories ?: JSONObject.NULL)
                    put("proteinGrams", f.proteinGrams ?: JSONObject.NULL)
                    put("carbGrams", f.carbGrams ?: JSONObject.NULL)
                    put("fatGrams", f.fatGrams ?: JSONObject.NULL)
                    put("fiberGrams", f.fiberGrams ?: JSONObject.NULL)
                    put("mealTime", f.mealTime ?: JSONObject.NULL)
                    put("isWholeFoodBased", f.isWholeFoodBased)
                    put("isProcessed", f.isProcessed)
                    put("isFermented", f.isFermented)
                    put("source", f.source)
                    put("notes", f.notes ?: JSONObject.NULL)
                    put("isSaved", f.isSaved)
                    put("createdAt", f.createdAt)
                    put("updatedAt", f.updatedAt)
                })
            }
        })

        // Body metric logs
        root.put("bodyMetricLogs", JSONArray().also { arr ->
            db.bodyMetricLogDao().observeAll().first().forEach { b ->
                arr.put(JSONObject().apply {
                    put("id", b.id)
                    put("date", b.date)
                    put("heightInches", b.heightInches ?: JSONObject.NULL)
                    put("bodyWeight", b.bodyWeight ?: JSONObject.NULL)
                    put("bodyFatPercentage", b.bodyFatPercentage ?: JSONObject.NULL)
                    put("calculatedBodyFatPercent", b.calculatedBodyFatPercent ?: JSONObject.NULL)
                    put("manualBodyFatPercent", b.manualBodyFatPercent ?: JSONObject.NULL)
                    put("isBodyFatOverridden", b.isBodyFatOverridden)
                    put("waistMeasurement", b.waistMeasurement ?: JSONObject.NULL)
                    put("neckMeasurement", b.neckMeasurement ?: JSONObject.NULL)
                    put("chestMeasurement", b.chestMeasurement ?: JSONObject.NULL)
                    put("armMeasurement", b.armMeasurement ?: JSONObject.NULL)
                    put("sleepHours", b.sleepHours ?: JSONObject.NULL)
                    put("energyLevel", b.energyLevel ?: JSONObject.NULL)
                    put("stressLevel", b.stressLevel ?: JSONObject.NULL)
                    put("sorenessLevel", b.sorenessLevel ?: JSONObject.NULL)
                    put("restingHeartRate", b.restingHeartRate ?: JSONObject.NULL)
                    put("stepCount", b.stepCount ?: JSONObject.NULL)
                    put("notes", b.notes ?: JSONObject.NULL)
                    put("createdAt", b.createdAt)
                    put("updatedAt", b.updatedAt)
                })
            }
        })

        // Workouts
        root.put("workouts", JSONArray().also { arr ->
            db.workoutDao().observeWorkouts().first().forEach { w ->
                arr.put(JSONObject().apply {
                    put("id", w.id)
                    put("date", w.date)
                    put("name", w.name)
                    put("durationMinutes", w.durationMinutes ?: JSONObject.NULL)
                    put("status", w.status)
                    put("overallRpe", w.overallRpe ?: JSONObject.NULL)
                    put("notes", w.notes ?: JSONObject.NULL)
                    put("createdAt", w.createdAt)
                    put("updatedAt", w.updatedAt)
                })
            }
        })

        // Workout exercises
        root.put("workoutExercises", JSONArray().also { arr ->
            db.workoutExerciseDao().observeAll().first().forEach { e ->
                arr.put(JSONObject().apply {
                    put("id", e.id)
                    put("workoutId", e.workoutId)
                    put("exerciseId", e.exerciseId)
                    put("setNumber", e.setNumber)
                    put("reps", e.reps ?: JSONObject.NULL)
                    put("weight", e.weight ?: JSONObject.NULL)
                    put("rpe", e.rpe ?: JSONObject.NULL)
                    put("restSeconds", e.restSeconds ?: JSONObject.NULL)
                    put("notes", e.notes ?: JSONObject.NULL)
                })
            }
        })

        // Recovery scores
        root.put("recoveryScores", JSONArray().also { arr ->
            db.recoveryScoreDao().observeAll().first().forEach { r ->
                arr.put(JSONObject().apply {
                    put("id", r.id)
                    put("date", r.date)
                    put("score", r.score)
                    put("label", r.label)
                    put("reasonText", r.reasonText ?: JSONObject.NULL)
                    put("sleepContribution", r.sleepContribution)
                    put("proteinContribution", r.proteinContribution)
                    put("sorenessContribution", r.sorenessContribution)
                    put("stressContribution", r.stressContribution)
                    put("workoutContribution", r.workoutContribution)
                    put("stepsContribution", r.stepsContribution)
                    put("restDayContribution", r.restDayContribution)
                    put("notes", r.notes ?: JSONObject.NULL)
                    put("createdAt", r.createdAt)
                    put("updatedAt", r.updatedAt)
                })
            }
        })

        // Daily recommendations
        root.put("dailyRecommendations", JSONArray().also { arr ->
            db.dailyRecommendationDao().getAll().forEach { rec ->
                arr.put(JSONObject().apply {
                    put("id", rec.id)
                    put("date", rec.date)
                    put("recommendationType", rec.recommendationType)
                    put("title", rec.title)
                    put("explanation", rec.explanation)
                    put("suggestedFocus", rec.suggestedFocus)
                    put("reasonBullets", rec.reasonBullets ?: JSONObject.NULL)
                    put("targetMuscleGroups", rec.targetMuscleGroups ?: JSONObject.NULL)
                    put("intensity", rec.intensity ?: JSONObject.NULL)
                    put("loadGuidance", rec.loadGuidance ?: JSONObject.NULL)
                    put("reasonSummary", rec.reasonSummary)
                    put("createdAt", rec.createdAt)
                    put("updatedAt", rec.updatedAt)
                })
            }
        })

        // Recovery activities
        root.put("recoveryActivities", JSONArray().also { arr ->
            db.recoveryActivityDao().observeAll().first().forEach { a ->
                arr.put(JSONObject().apply {
                    put("id", a.id)
                    put("workoutId", a.workoutId)
                    put("activityName", a.activityName)
                    put("status", a.status)
                    put("durationSeconds", a.durationSeconds ?: JSONObject.NULL)
                    put("durationMinutes", a.durationMinutes ?: JSONObject.NULL)
                    put("rpe", a.rpe ?: JSONObject.NULL)
                    put("notes", a.notes ?: JSONObject.NULL)
                })
            }
        })

        return root
    }
}
