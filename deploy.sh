#!/bin/bash

mvn clean install

scp target/api-0.0.1-SNAPSHOT.jar ubuntu@132.145.54.223:/home/ubuntu/api/

