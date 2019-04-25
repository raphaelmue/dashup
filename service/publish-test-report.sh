#!/bin/bash

MODULES=( "application" "model" "shared" "util" )

curl -Ls -o codacy-coverage-reporter "$(curl -Ls https://api.github.com/repos/codacy/codacy-coverage-reporter/releases/latest | jq -r '.assets | map({name, browser_download_url} | select(.name | contains("codacy-coverage-reporter-linux"))) | .[0].browser_download_url')"
chmod +x codacy-coverage-reporter
echo "Downloaded coverage reporter successfully!"

for i in MODULES; do
    if [[ -f "${MODULES[$i]}" ]]; then
        echo "Report file for module ${MODULES[$i]} was found! Publishing report ..."
        ./codacy-coverage-reporter report -l Java -r de.dashup.${MODULES[$i]}/target/site/jacoco/jacoco.xml
        echo "Published report file \"de.dashup.${MODULES[$i]}/target/site/jacoco/jacoco.xml\" successfully!"
    else
        echo "No report file for module ${MODULES[$i]} was found!"
    fi
done

./codacy-coverage-reporter final
echo "Finished publishing reports"