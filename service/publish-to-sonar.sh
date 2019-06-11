#!/bin/bash

# Copy jacoco report
cp ./target/jacoco.exec ./de.dashup.application/target/
cp ./target/jacoco.exec ./de.dashup.model/target/
cp ./target/jacoco.exec ./de.dashup.shared/target/
cp ./target/jacoco.exec ./de.dashup.util/target/

# Create test directory to avoid errors
mkdir -p ./de.dashup.shared/src/test/

# Determine current branch
BRANCH_NAME=$(git symbolic-ref --short -q HEAD)
echo "Current branch: ${BRANCH_NAME}"

# Checking whether current branch is a Pull Request
if [[ ${BRANCH_NAME} == PR-* ]] ; then
    PR_NUMBER="${BRANCH_NAME//PR-/}"
    echo "Pull Request number: ${PR_NUMBER}"
    mvn sonar:sonar -P sonar -Dsonar.projectKey=dashup -Dsonar.github.pullRequest=${PR_NUMBER}
else
    if [[ ${BRANCH_NAME} == "master" ]]; then
        mvn sonar:sonar -P sonar -Dsonar.projectKey=dashup
    else
        mvn sonar:sonar -P sonar -Dsonar.projectKey=dashup -Dsonar.branch.name=${BRANCH_NAME} -Dsonar.branch.target=master
    fi
fi