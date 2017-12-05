#!/usr/bin/env bash

echo "Building artifact:"
docker run -v ${PWD}:/rmf -w /rmf openjdk:8-alpine ./gradlew clean build shadowJar
echo "Done."

echo "Build runtime container:"
docker build -t vrapio/rmf-generator .
echo "Done."
