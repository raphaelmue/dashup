#!/bin/bash

# Create test directory to avoid errors
mkdir -p ./de.dashup.shared/src/test/

# Determine current branch
BRANCH_NAME=$(git symbolic-ref --short -q HEAD)
echo "Current branch: ${BRANCH_NAME}"

# Analyzing project
mvn sonar:sonar -P sonar -Dsonar.projectKey=dashup -Dsonar.branch.name=${BRANCH_NAME}