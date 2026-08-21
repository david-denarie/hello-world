# Text Transformer — Java (Spring Boot) + Angular

Application full-stack de transformation de texte :
- **Front-end** : application **Angular 22** (`frontend/`) avec une page d'accueil et des pages dédiées pour chaque transformation.
- **Back-end** : API REST en **Java 17 / Spring Boot 3.3** avec sécurité (Spring Security, validation, rate limiting).

## Structure du projet

```
text-reverser/
├── pom.xml                                         # Back-end Maven / Spring Boot
├── Dockerfile                                      # Build multi-stage (Node + Maven + JRE)
├── docker-compose.yml                              # Lancement Docker simplifié
├── src
│   ├── main/java/com/example/textreverser
│   │   ├── TextReverserApplication.java            # Point d'entrée Spring Boot
│   │   ├── controller/TextController.java          # API REST : /api/health, /api/reverse, /api/uppercase
│   │   ├── dto/TextRequest.java                    # DTO entrée (avec validation)
│   │   ├── dto/TextResponse.java                   # DTO sortie (original + result)
│   │   └── config/
│   │       ├── SecurityConfig.java                 # CORS, headers sécurité, session stateless
│   │       ├── RateLimitingFilter.java             # Rate limit 20 req/min par IP
│   │       ├── WebConfig.java                      # Sert le frontend Angular (SPA fallback)
│   │       ├── RequestLoggingConfig.java           # Log du body des requêtes (dev)
│   │       └── GlobalExceptionHandler.java         # Erreurs de validation → 400 propre
│   ├── main/resources/
│   │   ├── application.properties                  # Profil par défaut (HTTPS, port 8443)
│   │   ├── application-docker.properties           # Profil Docker (HTTP, port 8080)
│   │   └── keystore.p12                            # Certificat auto-signé (dev uniquement)
│   └── test/.../TextControllerTest.java            # Tests unitaires back
└── frontend/                                       # Application Angular 22
    ├── package.json
    ├── angular.json
    ├── proxy.conf.json                             # Redirige /api → https://localhost:8443
    └── src/app/
        ├── app.component.ts                        # Shell avec router-outlet
        ├── app.config.ts                           # Providers (HttpClient, Router)
        ├── app.routes.ts                           # Routes : /, /reverse, /uppercase
        ├── text-reverser.service.ts                # Service HTTP (reverse + uppercase)
        └── pages/
            ├── home/home.component.*               # Page d'accueil avec menu
            ├── reverse/reverse.component.*         # Page inversion de texte
            └── uppercase/uppercase.component.*     # Page mise en majuscules
```

## Prérequis

### Développement local
- **Java 17+** et **Maven 3.8+**
- **Node.js 22+** et **npm**

### Déploiement Docker
- **Docker** et **Docker Compose**

## Développement local

### Lancer le back-end (HTTPS, port 8443)

```bash
./mvnw spring-boot:run
```

L'API écoute sur **https://localhost:8443**.

### Lancer le front-end (port 4200)

```bash
cd frontend
npm install
npm start
```

Ouvrez **http://localhost:4200**. Le proxy redirige `/api/*` vers le backend HTTPS.

## API REST

| Méthode | URL              | Corps (JSON)            | Réponse (JSON)                                    |
|---------|------------------|-------------------------|---------------------------------------------------|
| GET     | `/api/health`    | —                       | `{ "status": "UP" }`                              |
| POST    | `/api/reverse`   | `{ "text": "bonjour" }` | `{ "original": "bonjour", "result": "ruojnob" }`  |
| POST    | `/api/uppercase` | `{ "text": "bonjour" }` | `{ "original": "bonjour", "result": "BONJOUR" }`  |

### Validation

- Le champ `text` est obligatoire (non vide) et limité à 10 000 caractères.
- `Content-Type: application/json` requis.
- Rate limit : 20 requêtes/minute par IP.

### Tester avec curl

```bash
curl -k -X POST https://localhost:8443/api/reverse -H "Content-Type: application/json" -d '{"text":"bonjour"}'
curl -k -X POST https://localhost:8443/api/uppercase -H "Content-Type: application/json" -d '{"text":"bonjour"}'
```

## Déploiement Docker

```bash
# Build et lancement
docker compose up --build

# En arrière-plan
docker compose up --build -d

# Arrêter
docker compose down
```

L'application est accessible sur **http://localhost:8080** (HTTP, le TLS est terminé par un reverse proxy en production).

### Déployer sur un autre poste

```bash
# Exporter l'image
docker save hello-world-text-reverser:latest | gzip > text-reverser.tar.gz

# Sur l'autre poste
docker load < text-reverser.tar.gz
docker run -p 8080:8080 hello-world-text-reverser:latest
```

## Tests

```bash
# Back-end
./mvnw test

# Front-end
cd frontend && npm test
```

## Sécurité

- CORS restreint aux origines autorisées
- Headers HTTP : HSTS, CSP, X-Content-Type-Options, X-Frame-Options
- Validation des entrées (Bean Validation)
- Rate limiting par IP (Bucket4j)
- HTTPS en développement (certificat auto-signé)
- Session stateless, CSRF désactivé (API REST)
