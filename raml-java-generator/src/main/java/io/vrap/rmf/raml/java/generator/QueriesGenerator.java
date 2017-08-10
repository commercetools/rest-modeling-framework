package io.vrap.rmf.raml.java.generator;

import com.squareup.javapoet.*;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.BodyType;
import io.vrap.rmf.raml.model.responses.Response;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.TypedElement;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class QueriesGenerator {
    private final String packageName;
    private final File generateTo;

    public QueriesGenerator(String packageName, File generateTo) {
        this.packageName = packageName;
        this.generateTo = generateTo;
    }

    public void generate(final Resource resource) {
        final Method getMethod = resource.getMethod(HttpMethod.GET);
        if (getMethod != null) {
            final Optional<Response> successfulResponse = getMethod.getResponses().stream()
                    .filter(response -> response.getStatusCode().equals("200"))
                    .findFirst();
            if (successfulResponse.isPresent()) {
                final Response response = successfulResponse.get();
                if (response.getBodies().size() == 1) {
                    final BodyType body = response.getBodies().get(0);
                    final AnyType namedType = getNamedType(body);
                    if (namedType != null) {
                        ClassName resourceType = ClassName.get("types", namedType.getName());
                        ParameterizedTypeName sphereRequest = ParameterizedTypeName.get(
                                ClassName.get("io.sphere.sdk.client", "SphereRequest"),
                                resourceType);
                        final ClassName httpRequestIntentType = ClassName.get("io.sphere.sdk.client", "HttpRequestIntent");
                        final ClassName httpMethodType = ClassName.get("io.sphere.sdk.http.HttpMethod", "GET");
                        final MethodSpec httpRequestIntentMethod = MethodSpec.methodBuilder("httpRequestIntent")
                                .addModifiers(Modifier.PUBLIC)
                                .addAnnotation(Override.class)
                                .addCode("return $T.of($L, \"\");\n", httpRequestIntentType, httpMethodType)
                                .returns(httpRequestIntentType)
                                .build();
                        ClassName sphereJsonUtilsType = ClassName.get("io.sphere.sdk.json", "SphereJsonUtils");
                        final ClassName httpResponseType = ClassName.get("io.sphere.sdk.http","HttpResponse");
                        final ClassName javaType = ClassName.get("com.fasterxml.jackson.databind", "JavaType");
                        final MethodSpec deserializeMethod = MethodSpec.methodBuilder("deserialize")
                                .addModifiers(Modifier.PUBLIC)
                                .addAnnotation(Override.class)
                                .addParameter(httpResponseType, "httpResponse", Modifier.FINAL)
                                .returns(resourceType)
                                .addCode("final $T javaType = $T.convertToJavaType($T.class);\n", javaType, sphereJsonUtilsType, resourceType)
                                .addCode("return $T.readObject(httpResponse.getResponseBody(), javaType);\n", sphereJsonUtilsType)
                                .build();
                        final ClassName queryTypeName = ClassName.get(packageName, namedType.getName() + "Get");
                        final MethodSpec factoryMethod = MethodSpec.methodBuilder("of")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(queryTypeName)
                                .addCode("return new $T();\n", queryTypeName)
                                .build();
                        final ClassName baseType = ClassName.get("io.sphere.sdk.models", "Base");
                        final TypeSpec queryType = TypeSpec.classBuilder(queryTypeName)
                                .superclass(baseType)
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                .addSuperinterface(sphereRequest)
                                .addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build())
                                .addMethod(deserializeMethod)
                                .addMethod(httpRequestIntentMethod)
                                .addMethod(factoryMethod)
                                .build();
                        generateFile(queryType);
                    }
                }
            }
        }
    }


    private void generateFile(final TypeSpec typeSpec) {
        final JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();
        try {
            javaFile.writeTo(generateTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AnyType getNamedType(final TypedElement typedElement) {
        AnyType namedType = typedElement.getType();
        while (namedType.getName() == null && namedType.getType() != null) {
            namedType = namedType.getType();
        }
        return namedType;
    }
}
