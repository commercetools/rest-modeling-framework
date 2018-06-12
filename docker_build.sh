#!/usr/bin/env bash

echo "Build runtime container:"
docker build --rm -t vrapio/rmf-generator .
echo "Done."
