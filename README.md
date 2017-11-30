 The rest modeling framework provides an EMF based model for RAML api definition files.

### Docker

To start vrap using docker use the following command

```
docker run --rm -v<RAML-definition-directory>:/api -v<output-directory>:/out vrapio/rmf-generator -l postman /api/update-actions.raml
```
