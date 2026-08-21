# ─── Stage 1 : Build Angular ──────────────────────────────────────────────────
FROM node:22-alpine AS frontend-build

WORKDIR /app/frontend

# Installer les dépendances (cache layer)
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci

# Build de production
COPY frontend/ ./
RUN npx ng build --configuration production


# ─── Stage 2 : Build Spring Boot ─────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS backend-build

WORKDIR /app

# Copier le Maven Wrapper et le pom
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Télécharger les dépendances (cache layer)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copier les sources backend
COPY src src

# Copier le build Angular dans les ressources statiques
COPY --from=frontend-build /app/frontend/dist/text-reverser-frontend/browser src/main/resources/static

# Packager le JAR (sans les tests, ils ont été validés en dev)
RUN ./mvnw package -DskipTests -B


# ─── Stage 3 : Image runtime légère ──────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Créer un utilisateur non-root
RUN addgroup -S app && adduser -S app -G app
USER app

# Copier le JAR depuis le stage de build
COPY --from=backend-build /app/target/text-reverser-1.0.0.jar app.jar

# Port exposé (HTTP en production, le TLS est géré par un reverse proxy)
EXPOSE 8080

# Healthcheck basique
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
