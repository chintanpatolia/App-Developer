# Claude Skills Setup

## Installed Skills

### `agent-skills-context-engineering`
**Path:** `.claude/skills/agent-skills-context-engineering/SKILL.md`  
**Skill name (frontmatter):** `context-engineering-collection`  
**Source:** Agent-Skills-for-Context-Engineering v2.3.0 (root package SKILL.md)

**What it is:** A meta-skill / orchestration index for a 15-skill collection covering context engineering, multi-agent architectures, memory system design, tool design, agent evaluation, harness engineering, and context optimization. It does not duplicate the individual skills — it provides cross-skill "when to combine" guidance and activation triggers that the individual skills omit.

**When it activates:**
- Building or debugging agent systems
- Designing multi-agent or orchestrator/worker architectures
- Optimizing context window usage or token efficiency
- Implementing memory or persistence layers for agents
- Evaluating or harness-engineering autonomous agent loops

**When it should NOT activate:**
- Normal Android UI edits, screen layout, or feature work
- Room migrations, ViewModel changes, or domain logic
- Any task that is purely about the Daily Health Coach app and does not involve agent workflow or context optimization

**Individual skills already installed** (all 15 referenced by this meta-skill):
`context-fundamentals`, `context-degradation`, `context-compression`, `context-optimization`, `multi-agent-patterns`, `memory-systems`, `tool-design`, `filesystem-context`, `hosted-agents`, `latent-briefing`, `evaluation`, `advanced-evaluation`, `harness-engineering`, `project-development`, `bdi-mental-states`

## Commit Recommendation

**Keep `.claude/skills/` out of version control for this repo.**

Why: These are developer-tooling assets tied to the Claude Code environment, not Android app code. Committing them would add noise to the `Health-Tracking-App` branch, conflict with teammates who have different Claude setups, and bloat the repo history with skill updates unrelated to the app. The `main` branch should stay clean for Android-only changes.

If you want the skill setup reproducible across machines, the right place is the global `~/.claude/` config, not the project repo.
