# --- ETAPA 1: CONSTRUCCIÓN (BUILD) ---
# Usamos una imagen de Maven para compilar el código
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Copiamos todo tu código al contenedor
COPY . .

# Ejecutamos el comando para compilar (saltando los tests para ir rápido)
RUN mvn clean package -DskipTests

# --- ETAPA 2: EJECUCIÓN (RUN) ---
# Usamos una imagen ligera de Java para correr la app
FROM eclipse-temurin:21-jdk-alpine

# Copiamos solo el archivo .jar generado en la etapa anterior
COPY --from=build /target/*.jar app.jar

# Arrancamos la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]