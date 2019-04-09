# Coding conventions of dashup

## 1 Git

- Commit only relevant files
- Commit message has to contain branch name, e.g. "master: [...]"
- If you are committing an issue, the commit message should be something like: "issue #123". This allows us to navigate withing GitHub.
- Do not commit on master branch (except for documentation changes or similar)
- Reviews: 
    - When a feature is implemented, open a pull request and add all other contributors as reviewers
    - Take reviews serious!
    - Do not merge your issue or feature branch if there is no pull request

## 2 Maven

- Add only dependencies if they are necessary
- The same applies to plugins

## 3 Coding

### 3.1 General

- Run formatter before committing
- Do not commit errors
- Do not commit warnings
- No codacy issues

### 3.2 Naming

- Use camel case (no underscores, etc.) in Java
- Use descriptive names for classes, methods, variables etc.
- Do not use abbreviations

## 4 Consequences

Each violation of the conventions leads to 1 beer. At the end of the project, all contributors meet and drink that beer until they are "sternhagelvoll".