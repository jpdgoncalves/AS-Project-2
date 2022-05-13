#!/bin/bash

KAFKA_FOLDER="/home/mathebrard/Téléchargements/kafka_2.13-3.1.0"

$KAFKA_FOLDER/bin/zookeeper-server-start.sh $KAFKA_FOLDER/config/zookeeper.properties &
$KAFKA_FOLDER/bin/kafka-server-start.sh ./server.properties &