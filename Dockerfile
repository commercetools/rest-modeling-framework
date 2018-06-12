FROM openjdk:8 as builder

WORKDIR /rmf
COPY . /rmf

RUN ./gradlew clean shadowJar

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=builder /rmf/raml-generic-generator/build/libs/rmf-gen.jar /app/rmf-gen.jar
ADD rmf-gen.sh /app/rmf-gen.sh

ENV JAVA_OPTS  ""
EXPOSE 5050
EXPOSE 5005
ENTRYPOINT ["./rmf-gen.sh"]
