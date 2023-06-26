FROM openjdk:17-oracle
WORKDIR /app
COPY target/MotorcycleShop-0.0.1-SNAPSHOT.jar /app
CMD ["java", "-jar", "MotorcycleShop-0.0.1-SNAPSHOT.jar"]
