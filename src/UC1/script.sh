#./home/mathebrard/Téléchargements/kafka_2.13-3.1.0/bin/kafka-server-start.sh config/server.properties &
#./home/mathebrard/Téléchargements/kafka_2.13-3.1.0/bin/zookeeper-server-start.sh config/zookeeper.properties &

#parallel -u ::: './home/mathebrard/Téléchargements/kafka_2.13-3.1.0/bin/kafka-server-start.sh config/server.properties' './home/mathebrard/Téléchargements/kafka_2.13-3.1.0/bin/zookeeper-server-start.sh config/zookeeper.properties'