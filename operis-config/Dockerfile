# Utiliser une image de base Java 22.0.1
FROM openjdk:22-jdk

# Ajouter un volume pour les journaux
VOLUME /tmp

# Ajouter le fichier JAR de l'application dans l'image Docker
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Exposer le port sur lequel votre application s'exécute
EXPOSE 8888

# Définir le point d'entrée pour l'exécution de l'application
ENTRYPOINT ["java", "-jar", "/app.jar","--spring.profiles.active=docker"]
