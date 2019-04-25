#!/bin/bash

REPORT_FILE="build/jacoco/jacoco.exec"

if [[ -f "$REPORT_FILE" ]]; then
    echo "Report file was found! Publishing report ..."
    curl -Ls -o codacy-coverage-reporter "$(curl -Ls https://api.github.com/repos/codacy/codacy-coverage-reporter/releases/latest | jq -r '.assets | map({name, browser_download_url} | select(.name | contains("codacy-coverage-reporter-linux"))) | .[0].browser_download_url')"
    chmod +x codacy-coverage-reporter
    ./codacy-coverage-reporter report -l Java -r ${REPORT_FILE}
    echo "Published report file successfully!"
else
    echo "No report file was found!"
fi