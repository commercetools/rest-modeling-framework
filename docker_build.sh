#!/usr/bin/env bash

echo "Building artifact:"
docker run --rm -v ${PWD}:/rmf -v${HOME}/.gradle:/root/.gradle -w /rmf openjdk:8-alpine ./gradlew clean build shadowJar
echo "Done."

echo "Build runtime container:"
docker build --rm -t vrapio/rmf-generator .
echo "Done."
