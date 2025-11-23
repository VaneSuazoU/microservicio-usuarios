# ---------- STAGE 1: BUILD ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos pom y descargamos dependencias primero (caché)
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copiamos el código
COPY src ./src

# Compilamos el jar
RUN mvn -q clean package -DskipTests


# ---------- STAGE 2: RUNTIME ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/target/*.jar app.jar

# Copiar la base de datos SQLite
COPY data/app.db /app/data/app.db

# Puerto por defecto (lo sobreescribes por env en compose)
EXPOSE 8081

# Perfil docker (lo setea compose)
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java","-jar","app.jar"]
