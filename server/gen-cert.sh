#!/bin/sh

ALIAS=tspringtest
TARGET_DIR=src/main/resources
TARGET_FILE=cert.p12
TARGET=$TARGET_DIR/$TARGET_FILE

keytool -genkeypair -alias $ALIAS -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore $TARGET -validity 3650
