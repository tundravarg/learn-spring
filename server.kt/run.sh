#!/bin/sh

WD=target
JAR=server-1.0-SNAPSHOT.jar
MAIN_CLASS=tuman.learnspring.server.AppKt

cd "$WD" && java -cp "$JAR:lib/*" $MAIN_CLASS $*