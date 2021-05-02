#!/bin/bash

java -jar -DDB_USER=$DB_USER -DDB_PASS=$DB_PASS -DJDBC_URL=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME -Dsystem.host.name=$HOSTNAME target/orderdata-0.0.1-SNAPSHOT.jar &