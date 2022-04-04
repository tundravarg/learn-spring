#!/bin/sh

WD=target
JAR=server-1.0-SNAPSHOT.jar
MAIN_CLASS=tuman.learnspring.server.AppKt

# Regular jar and dependencies in "lib" beside
# cd "$WD" && java -cp "$JAR:lib/*" $MAIN_CLASS $*

# Spring Boot application
java -jar $WD/$JAR $*