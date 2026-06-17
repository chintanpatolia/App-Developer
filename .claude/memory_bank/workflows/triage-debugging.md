# Triage & Debugging Workflow

## Steps

1. **Memory bank first** — read `.claude/memory_bank/troubleshooting/` and `decisions/` relevant to the symptom.
2. **Invoke ponytail** — run `/ponytail` for structured root-cause triage before opening any source file.
3. **Understand the symptom** — reproduce or clearly describe the bug; note which screen/feature/layer is affected.
4. **Trace the minimal flow** — identify the layer:
   - UI (`Screen.kt`, `UiState.kt`)
   - ViewModel (`ViewModel.kt`, `ViewModelFactory`)
   - Domain (use case, calculator, service)
   - Repository (`*RepositoryImpl.kt`)
   - Database (entity, DAO, migration in `AppDatabaseProvider`)
   - Navigation (`AppScreen`, `DailyHealthCoachApp.kt`)
   - Build (Gradle, Room schema, annotation processor)
5. **Inspect minimum files** — read only the files in the identified layer; do not scan the full repo.
6. **Identify root cause** — state it explicitly before proposing any fix.
7. **Propose smallest safe fix** — one targeted change; no surrounding cleanup.
8. **List exact files** — enumerate every file that will change before editing.
9. **Edit only approved files.**
10. **Build** — run `$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug`.
11. **Summarize** — root cause, files changed, build result, and suggested git commit command.
