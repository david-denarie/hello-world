# Text Reverser — Java (Spring Boot) + Angular

Projet full-stack démontrant :
- **Front-end** : une application **Angular 18** (`frontend/`) avec un champ de saisie texte et un bouton « Envoyer ».
- **Back-end** : une API REST en **Java / Spring Boot** qui reçoit le texte et le retourne **inversé**.

## Structure du projet

```
text-reverser/
├── pom.xml                                        # Back-end Maven / Spring Boot
├── src
│   ├── main/java/com/example/textreverser
│   │   ├── TextReverserApplication.java           # Point d'entrée Spring Boot
│   │   ├── controller/TextController.java          # API REST POST /api/reverse
│   │   └── dto/{TextRequest,TextResponse}.java     # Objets JSON entrée/sortie
│   ├── main/resources/application.properties       # Port 8080
│   └── test/.../TextControllerTest.java            # Tests unitaires back
└── frontend                                        # Application Angular
    ├── package.json
    ├── angular.json
    ├── proxy.conf.json                             # Redirige /api -> localhost:8080
    ├── tsconfig*.json
    └── src
        ├── index.html
        ├── main.ts
        ├── styles.css
        └── app
            ├── app.config.ts                       # provideHttpClient()
            ├── app.component.ts / .html / .css     # UI : champ + bouton
            ├── app.component.spec.ts               # Test unitaire front
            └── text-reverser.service.ts            # Appel HTTP à l'API
```

## Prérequis
- **Java 17+** et **Maven 3.8+** (back-end)
- **Node.js 18+** et **npm** (front-end Angular)

## 1) Lancer le back-end (API REST)

```bash
cd text-reverser
mvn spring-boot:run
```
L'API écoute sur **http://localhost:8080**.

| Méthode | URL            | Corps (JSON)            | Réponse (JSON)                                     |
|---------|----------------|-------------------------|----------------------------------------------------|
| POST    | `/api/reverse` | `{ "text": "bonjour" }` | `{ "original": "bonjour", "reversed": "ruojnob" }` |

## 2) Lancer le front-end (Angular)

Dans un second terminal :

```bash
cd text-reverser/frontend
npm install
npm start            # = ng serve --proxy-config proxy.conf.json
```
Ouvrez ensuite **http://localhost:4200**.

> Le fichier `proxy.conf.json` redirige automatiquement les appels `/api/*`
> du serveur de dev Angular (4200) vers le back-end Spring Boot (8080),
> ce qui évite tout problème de CORS pendant le développement.

## Tester avec curl (back-end seul)

```bash
curl -X POST http://localhost:8080/api/reverse \
     -H "Content-Type: application/json" \
     -d '{"text":"bonjour"}'
```

## Lancer les tests

```bash
# Back-end
mvn test

# Front-end
cd frontend && npm test
```

## Build de production

```bash
# Back-end : JAR exécutable
mvn clean package
java -jar target/text-reverser-1.0.0.jar

# Front-end : build optimisé dans frontend/dist/
cd frontend && npm run build
```

> Pour un déploiement unifié, vous pouvez copier le contenu de
> `frontend/dist/text-reverser-frontend/browser/` dans
> `src/main/resources/static/` du back-end afin que Spring Boot serve
> directement l'application Angular sur le port 8080.
