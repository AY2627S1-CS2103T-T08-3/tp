# Project agent instructions

For every Java code change in this repository, use and follow the project skill at
`.agents/skills/seedu-java-coding-standard/SKILL.md`.

Treat the SE-EDU basic + intermediate Java coding standard as mandatory. Before completing a Java change:

1. Review changed Java files against the skill.
2. Run `./gradlew checkstyleMain` for production-code changes and `./gradlew checkstyleTest` for test-code changes.
3. Fix applicable violations rather than suppressing them, unless an existing project convention requires a documented
   exception.

Preserve the established package structure unless the task explicitly requests a package migration.
