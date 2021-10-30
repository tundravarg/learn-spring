#!/bin/sh

JAR_FILE=target/server-1.0-SNAPSHOT.jar
MAIN_CLASS=tuman.learnspring.server.App

java -cp $JAR_FILE $MAIN_CLASS
