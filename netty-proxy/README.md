mvn clean package

java -DlocalPort=8080 -DremoteHost=localhost -DremotePort=8888 -jar target/netty-proxy-1.0-SNAPSHOT-jar-with-dependencies.jar

curl http://localhost:8080/api/hello


