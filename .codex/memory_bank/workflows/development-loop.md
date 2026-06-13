# Development Loop (GSD-style)

1. **Plan** — state what will change and why before touching files.
2. **Inspect minimal files** — read only the files directly needed for the task.
3. **Edit smallest scope** — change the fewest files possible; prefer Edit over Write.
4. **Build** — validate after every code change:
   ```powershell
   $env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug
   ```
5. **Summarize changed files** — list each file and one-line reason before committing.
6. **Stage only intended files** — never `git add -A` or `git add .` blindly.
7. **Commit** — concise message focused on *why*, not *what*.
8. **Push** — only when explicitly asked.
9. **Update memory** — write only the relevant memory file(s); skip a full refresh.
