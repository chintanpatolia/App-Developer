# AGENTS.md — Codex Agent Workflow Guide

## Memory-First Workflow

1. **Query `.codex/memory_bank/` first** before reading source files. Check `decisions/`, `patterns/`, `architecture/`, and `troubleshooting/` for relevant context.
2. **Avoid full repo scans.** Use targeted `Grep` or `Glob` with specific paths or class names.
3. **Read only task-relevant files.** State which files you need and why before opening them.
4. **Make the smallest safe change.** Prefer `Edit` over `Write`; change the fewest files possible.
5. **List files before editing.** Declare every file that will change before making any edits.

## Android Build Validation

Run after every code change:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug
```

Do not report a task complete until the build passes.

## Git Safety

- Do **not** run destructive git commands (`git reset --hard`, `git clean -fd`, `git push --force`) without explicit user approval.
- Stage files by name — never `git add -A` or `git add .`.
- Do not commit or push unless the user asks.

## Skill / Memory Folder Policy

- `.codex/skills/` and `.claude/` folders are local tooling — do not commit them unless the user intentionally wants them versioned.
- After completing a task, update only the relevant `.codex/memory_bank/` files rather than re-discovering project context next session.

## Memory Bank Structure

```
.codex/memory_bank/
  decisions/       # ADRs and trade-offs
  patterns/        # Conventions and implementation patterns
  architecture/    # Module, layer, DI, navigation, DB schema notes
  troubleshooting/ # Known issues and proven fixes
  workflows/       # Development and memory-management workflows
```

See `.codex/memory_bank/README.md` for usage rules.
