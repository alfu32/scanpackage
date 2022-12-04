#!/bin/bash

rm -rf ./scans/*.json
java -jar ./target/scanpackage-1.0-SNAPSHOT-jar-with-dependencies.jar $*