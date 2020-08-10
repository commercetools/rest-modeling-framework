[![Build Status](https://travis-ci.com/vrapio/rest-modeling-framework.svg?branch=master)](https://travis-ci.com/vrapio/rest-modeling-framework)

The rest modeling framework provides an EMF based model for RAML api definition files.

### Using in your own project

The latest unstable release can be retrieved from [jcenter](https://bintray.com/vrapio/vrapio/rmf)  with:
```gradle
ext {
    rmfVersion = "0.2.0-20200810115228"
}

sourceCompatibility = 1.8

repositories {
    jcenter()
}

dependencies {
    compile "io.vrap.rmf:raml-model:${rmfVersion}"
}
```

The main entry point is the `io.vrap.rmf.raml.model.RamlModelBuilder` class, see the following code for an example on how to use it:

```java
  final URI fileURI = URI.createFileURI("/path/api.raml");
  final RamlModelResult<Api> modelResult = new RamlModelBuilder().buildApi(fileURI);
  final List<RamlDiagnostic> validationResults = modelResult.getValidationResults();

  if (validationResults.isEmpty()) {
      final Api api = modelResult.getRootObject();
  }  
```

### Docker

To start the RMF generator using docker use the following command

```
docker run --rm -v<RAML-definition-directory>:/api -v<output-directory>:/out vrapio/rmf-generator -l postman /api/update-actions.raml
```

### Bintray upload

Set environment variables BINTRAY_USER and BINTRAY_KEY to your bintray credentials.
Run gradle with
```
./gradlew clean build bintrayUpload --info
```
to upload the artifacts to the `rmf` repository in your bintray project.
