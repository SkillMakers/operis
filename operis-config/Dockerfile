# Utiliser une image de base Java 22.0.1
FROM openjdk:22-jdk

# Ajouter le fichier JAR de l'application dans l'image Docker
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Définir le point d'entrée pour l'exécution de l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
