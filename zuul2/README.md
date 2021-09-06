mvn clean package

java -Darchaius.deployment.environment=benchmark -DbackendService="localhost:8888" -jar target/zuul2-1.0-SNAPSHOT-jar-with-dependencies.jar

curl http://localhost:7001/api/hello