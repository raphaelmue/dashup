#!/bin/bash

MODULES=( application model shared util )

curl -Ls -o codacy-coverage-reporter "$(curl -Ls https://api.github.com/repos/codacy/codacy-coverage-reporter/releases/latest | jq -r '.assets | map({name, browser_download_url} | select(.name | contains("codacy-coverage-reporter-linux"))) | .[0].browser_download_url')"
chmod +x codacy-coverage-reporter
echo "Downloaded coverage reporter successfully!"
echo "------------------------------------------"

for module in "${MODULES[@]}"
do
    if [[ -f "de.dashup.${module}/target/site/jacoco/jacoco.xml" ]]; then
        echo "Report file for module ${module} was found! Publishing report ..."
        ./codacy-coverage-reporter report -l Java -r de.dashup.${module}/target/site/jacoco/jacoco.xml --partial
        echo "Published report file \"de.dashup.${module}/target/site/jacoco/jacoco.xml\" successfully!"
    else
        echo "No report file for module ${module} was found!"
    fi
    echo "------------------------------------------"
done

./codacy-coverage-reporter final
echo "------------------------------------------"
echo "Finished publishing reports successfully!"
echo "------------------------------------------"