#Stage-1
FROM eclipse-temurin:18 as build
# kijelőli a bázis könvtárat (a path-ok inen realatívak legyenek.
WORKDIR /app
# Maven könyvtár másolása
COPY .mvn/ ./.mvn
# A warapper (linuxos!) és a pom másolása
COPY mvnw pom.xml ./
# az mvnw script sorvégeit unix formátumra kell alakítani
RUN sed -i 's/\r$//' ./mvnw
# Naven függőségek beszerzése
RUN ./mvnw dependency:go-offline
# források másolása
COPY src ./src
# jar előállítása
RUN ./mvnw package

#Stzage-2
FROM eclipse-temurin:18-alpine
# A jar végleges helyére másolása
WORKDIR /app
COPY --from=build /app/target/icf-test-0.0.1-SNAPSHOT.jar ./icf-test-0.0.1-SNAPSHOT.jar
EXPOSE 1118
ENTRYPOINT ["java", "-jar", "./icf-test-0.0.1-SNAPSHOT.jar"]
