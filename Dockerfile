# syntax = docker/dockerfile:1.2
FROM clojure:openjdk-17 AS build

WORKDIR /
COPY . /

RUN clj -Sforce -T:build all

FROM azul/zulu-openjdk-alpine:17

COPY --from=build /target/paper-auth-standalone.jar /paper-auth/paper-auth-standalone.jar

EXPOSE $PORT

ENTRYPOINT exec java $JAVA_OPTS -jar /paper-auth/paper-auth-standalone.jar
