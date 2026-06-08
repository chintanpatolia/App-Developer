---
name: get-shit-done
description: Use when working with the Get Shit Done (GSD) framework, including spec-driven development workflows, GSD command behavior, configuration, agent roles, hooks, issue-driven orchestration, context monitoring, and the archived GSD 2 CLI/SDK resources.
---

# Get Shit Done

Use this skill for tasks involving the GSD framework or its archived repository contents. The bundled resources preserve the useful command definitions, scripts, hooks, templates, SDK source, agent role files, assets, and selected documentation from `get-shit-done-main.zip`.

## Quick Start

1. Read `references/docs/README.md` for the documentation map.
2. For user-facing workflow questions, read `references/docs/USER-GUIDE.md` and `references/docs/COMMANDS.md`.
3. For configuration questions, read `references/docs/CONFIGURATION.md`.
4. For architecture, CLI internals, or extension work, read `references/docs/ARCHITECTURE.md`, `references/docs/CLI-TOOLS.md`, and `references/AGENTS.md`.
5. For issue-driven work, read `references/docs/issue-driven-orchestration.md`.

## Bundled Resources

- `commands/`: GSD command prompt and workflow definitions.
- `get-shit-done/`: runtime workflow/template content.
- `agents/`: agent role files used by the framework.
- `hooks/`: runtime hook resources.
- `scripts/` and `bin/`: CLI and maintenance scripts.
- `sdk/`: TypeScript SDK source and related package files from the archive.
- `assets/`: visual and terminal assets.
- `references/docs/`: selected English documentation.
- `references/CONTEXT.md`: archived project context for deeper research.
- `references/TEST-EXAMPLES.md`: testing examples and patterns.

## Notes

- The archive README says GSD has moved to GSD Core in the Open GSD repository. Treat this installed skill as an archived local reference unless the user asks to migrate or update it.
- Do not run installer scripts or mutate the host project unless the user explicitly asks.
- Prefer reading the smallest relevant reference file instead of loading the large context files first.
