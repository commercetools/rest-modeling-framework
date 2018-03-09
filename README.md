 The rest modeling framework provides an EMF based model for RAML api definition files.

### Docker

To start the RMF generator using docker use the following command

```
docker run --rm -v<RAML-definition-directory>:/api -v<output-directory>:/out vrapio/rmf-generator -l postman /api/update-actions.raml
```

### Bintray upload

Set environment varaibles BINTRAY_USER and BINTRAY_KEY to your bintray credentials.
Run gradle with
```
./gradlew clean build bintrayUpload --info
```
to upload the artifacts to the `rmf` repository in your bintray project.
