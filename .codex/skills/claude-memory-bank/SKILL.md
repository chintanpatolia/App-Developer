---
name: claude-memory-bank
description: Use when setting up, adapting, querying, synchronizing, or maintaining a structured project memory bank for persistent coding context, including decisions, patterns, architecture notes, troubleshooting knowledge, context queries, stale-context checks, and memory-bank cleanup workflows converted from the Claude Memory Bank extension.
---

# Claude Memory Bank

Use this skill for persistent project knowledge workflows: creating or adapting a memory bank, querying focused context before work, syncing new decisions and patterns after work, checking stale documentation, and archiving completed feature context.

## Core Workflow

1. Look for an existing memory bank before starting memory-related work. Common Claude layout is `.claude/memory_bank/`; adapt paths to the current project if the user wants a Codex-native layout.
2. Prefer granular memory files over broad archives:
   - `decisions/` for ADRs and trade-offs.
   - `patterns/` for conventions and repeated implementation patterns.
   - `architecture/` for module, service, and component structure.
   - `troubleshooting/` for known issues and proven fixes.
   - `stash/` only when explicitly relevant.
3. Retrieve focused context first, using narrow path or feature scopes and small limits.
4. Update memory after meaningful implementation, bug investigation, architectural decisions, or workflow discoveries.
5. Treat archives as read-only last-resort context unless the user asks to rebuild or restore from them.

## Bundled Resources

- `commands/context/`: source command definitions for context query, memory update, context diff, cleanup, stale checks, and archive rebuild workflows.
- `workflows/memory-bank/`: feature, hotfix, hygiene, delta-sync, and just-in-time context workflows.
- `agents/`: original specialized agent role prompts for code search, context query, memory synchronization, stale-context analysis, UX design, verification, and execution.
- `references/README.md`: original project overview and usage examples.
- `references/CLAUDE.md`: original global Claude guidance; adapt it carefully because Codex system and user instructions take precedence.
- `references/settings.json`: original Claude permissions/settings reference.

## Adaptation Notes

- Do not install files into a user's global Claude directory unless the user explicitly asks.
- Do not create or rewrite a project memory bank unless the user asks for setup, sync, rebuild, cleanup, or similar memory-bank work.
- When porting command behavior, use the bundled command markdown as reference instructions rather than assuming Claude slash commands exist in Codex.
- Exclude local memory-bank archives or generated memory files from commits unless the user explicitly wants them versioned.
