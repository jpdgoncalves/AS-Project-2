#!/bin/bash
echo "hello"

KAFKA_FOLDER="/home/mathebrard/Téléchargements/kafka_2.13-3.1.0"
SERVER_PROPERTIES="/home/mathebrard/Bureau/Polytech/SI4/Portugal/Cours/SW Architecture/Practical Assignement/PA2/git/AS-Project-2/src/UC5/Config/server.properties"

$KAFKA_FOLDER/bin/zookeeper-server-start.sh $KAFKA_FOLDER/config/zookeeper.properties &
$KAFKA_FOLDER/bin/kafka-server-start.sh ./server.properties &