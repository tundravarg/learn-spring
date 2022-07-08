#!/bin/sh

SRC_FILE=cert.pem
DST_DIR=src/main/resources
DST_FILE=$DST_DIR/cacerts.jks
ALIAS=test
STORE_PASSWORD=store_pass
KEY_PASSWORD=key_pass

keytool -import -v -trustcacerts -alias $ALIAS -file $SRC_FILE -keypass $KEY_PASSWORD -keystore $DST_FILE -storepass $STORE_PASSWORD
