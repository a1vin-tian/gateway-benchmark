
mvn clean package

java -DbackendService="http://localhost:8888/api/hello" -DproxyContex=/api/hello -jar target/demo3-0.0.1-SNAPSHOT.jar

curl http://localhost:8080/api/hello