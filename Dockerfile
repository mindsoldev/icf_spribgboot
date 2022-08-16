FROM eclipse-temurin:18
# kijelőli a bázis könvtárat (a path-ok inen realatívak legyenek.
WORKDIR /app
# Maven könyvtár másolása
COPY .mvn/ ./.mvn
# A warapper (linuxos!) és a pom másolása
COPY mvnw pom.xml .
# az mvnw script sorvégeit unix formátumra kell alakítani
RUN sed -i 's/\r$//' ./mvnw
# Naven függőségek beszerzése
RUN ./mvnw dependency:go-offline
# források másolása
COPY src ./src
# jar előállítása
RUN ./mvnw package
# A jar végleges helyére másolása
RUN mv ./target/icf-test-0.0.1-SNAPSHOT.jar ./
run ls -l

EXPOSE 1118
ENTRYPOINT ["java", "-jar", "./icf-test-0.0.1-SNAPSHOT.jar"]
