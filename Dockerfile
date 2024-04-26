FROM openjdk:17-oracle

WORKDIR /app

COPY target/equipos-futbol-0.0.1-SNAPSHOT.jar /app/equipos-futbol-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "equipos-futbol-0.0.1-SNAPSHOT.jar"]