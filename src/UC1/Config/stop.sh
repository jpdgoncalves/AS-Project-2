#!/bin/bash

#kills every process having "zookeeper" in his name
pkill -f "zookeeper"
#kills every process having "kafka" in his name
pkill -f "kafka"

rm -r /tmp/kafka-logs/
rm -r /tmp/zookeeper/
rm /home/mathebrard/Téléchargements/kafka_2.13-3.1.0/logs/*