#!/usr/bin/env bash

java -jar mock-service/target/mock-service-*-jar-with-dependencies.jar server mock-service/app-config-local.yml
