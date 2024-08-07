#!/bin/bash

set -e

# Decrypt credentials
echo "decrypt"
echo ${DECRYPTER} | base64 --decode > decrypter.json
echo "- done"
echo "key"
echo ${SIGNING_KEY} | base64 --decode > signing_key.enc
echo "- done"
echo "phrase"
echo ${PASSPHRASE} | base64 --decode > signing_passphrase.enc
echo "- done"

gcloud auth activate-service-account --key-file decrypter.json

echo "Decrypt signing secrets"

gcloud kms decrypt \
  --project=commercetools-platform \
  --location=global \
  --keyring=devtooling \
  --key=java-sdk-v2 \
  --ciphertext-file=signing_key.enc \
  --plaintext-file=signing_key.asc

gcloud kms decrypt \
  --project=commercetools-platform \
  --location=global \
  --keyring=devtooling \
  --key=java-sdk-v2 \
  --ciphertext-file=signing_passphrase.enc \
  --plaintext-file=signing_passphrase.txt

# Import the GPG key
set +e
echo "Importing the signing key"
gpg --import --no-tty --batch --yes signing_key.asc
echo " - done"
set -e

# List available GPG keys
gpg -K

KEYNAME=`gpg --with-colons --keyid-format long --list-keys devtooling@commercetools.com | grep fpr | cut -d ':' -f 10`

mkdir -p ~/.gradle
touch ~/.gradle/gradle.properties

echo "signing.gnupg.executable=gpg" >> ~/.gradle/gradle.properties
echo "signing.gnupg.keyName=$KEYNAME" >> ~/.gradle/gradle.properties
echo "signing.gnupg.passphrase=$(<signing_passphrase.txt)" >> ~/.gradle/gradle.properties
echo "gradle.publish.key=${GRADLE_PUBLISH_KEY}" >> ~/.gradle/gradle.properties
echo "gradle.publish.secret=${GRADLE_PUBLISH_SECRET}" >> ~/.gradle/gradle.properties

rm -rf signing_passphrase.txt signing_passphrase.enc signing_key.enc decrypter.json signing_key.asc

