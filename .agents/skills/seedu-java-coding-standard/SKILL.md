---
name: seedu-java-coding-standard
description: Apply and review this project's Java code using the SE-EDU basic + intermediate Java coding standard. Use for every Java implementation, refactor, review, or test change in this repository.
---

# SE-EDU Java coding standard

Follow the [SE-EDU basic + intermediate Java coding standard](https://se-education.org/guides/conventions/java/intermediate.html).
For topics it does not cover, follow the Google Java Style Guide. Preserve established repository conventions where
they are stricter, especially the configured import order.

## Required rules

- Use lowercase package names, PascalCase noun names for classes and enums, camelCase verb names for methods,
  camelCase variables, and SCREAMING_SNAKE_CASE constants.
- Keep names in English. Use boolean names that read as conditions, such as `isValid` or `hasData`, and plural names
  for collections.
- Indent with four spaces and never tabs. Prefer lines below 110 characters and never exceed 120 characters.
- Wrap continuation lines by eight additional spaces. Break after commas and before operators when wrapping.
- Use K&R braces. Always use braces for loop and conditional bodies, and place conditional bodies on separate lines.
- Surround operators with spaces, put spaces after commas and reserved words, and separate logical blocks with blank
  lines.
- Use explicit, minimal, consistently ordered imports; never use wildcard imports.
- Attach array brackets to the type. Initialize variables at declaration where practical and keep their scope minimal.
- Keep fields non-public unless they are constants or the established value-object pattern in this repository applies.
- Write comments in English using American spelling. Add descriptive Javadoc to public classes and public methods,
  except straightforward getters/setters, exact overrides, and test methods. Start method summaries with a verb such as
  `Returns`, `Adds`, or `Creates`.
- Mark intentional switch fall-through with `// Fallthrough`.

## Workflow

1. Read the surrounding code and `config/checkstyle/checkstyle.xml`; follow existing conventions when they are more
   specific than the general standard.
2. Apply these rules while editing rather than as a separate formatting rewrite. Do not reformat unrelated code.
3. Review every changed Java file for semantic naming, Javadoc, wrapping, braces, whitespace, imports, and field
   visibility.
4. Run the relevant Gradle Checkstyle task. Fix violations unless a documented project exception applies.

Do not use Checkstyle success as the only proof of compliance: it cannot enforce every naming, documentation, scope,
or readability rule in the standard.
