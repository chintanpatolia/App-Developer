# Gradle Build Issues

## Symptoms
- `Could not find com.android.tools.build:gradle:...` — Gradle plugin version not in repos.
- `Kotlin daemon connection error` — Kotlin compiler daemon crashed.
- `JAVA_HOME` errors on Windows — build script cannot find JDK.
- `Execution failed for task ':app:kspDebugKotlin'` — KSP (Room annotation processing) failed.
- `Room schema validation failed` — entity and migration out of sync.

## Diagnostics

### JAVA_HOME not set (Windows)
```powershell
# Always use the full command from CLAUDE.md:
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug
```

### KSP / Room compile error
```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug 2>&1 | Select-String -Pattern "error:|warning:|Migration|Schema"
```
KSP errors are almost always Room annotation issues: wrong entity field type, missing DAO method, or schema mismatch.

### Daemon issues
```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat --stop
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug
```

### Clean build
```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat clean assembleDebug
```

## Fix
| Symptom | Fix |
|---------|-----|
| JAVA_HOME missing | Prepend `$env:JAVA_HOME='...\jbr';` to every gradlew command |
| Room schema mismatch | Check entity matches migration SQL; bump version if not done |
| KSP error on DAO | Verify `@Query` SQL references correct table/column names |
| Daemon crashed | Run `gradlew --stop` then rebuild |
| Dependency not found | Check network / corporate proxy; try `--refresh-dependencies` |

## Affected Versions
All versions. Windows-specific `JAVA_HOME` issue is always present.

## Prevention
- Use the exact build command from `CLAUDE.md` including the `$env:JAVA_HOME` prefix.
- Never use `gradlew` directly on Windows without the `$env:JAVA_HOME` prefix.
- After any entity change: build immediately to catch Room errors early.

## Related
- `troubleshooting/room-migration-failure.md`
- `patterns/room-migration.md`
