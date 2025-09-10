# Trivia Application

Een eenvoudige webapplicatie voor trivia-vragen met een Java Spring Boot backend en een angular frontend.

## Overzicht

Deze applicatie biedt gebruikers de mogelijkheid om trivia-vragen te beantwoorden via een gebruiksvriendelijke interface. De backend fungeert als tussenliggende API tussen de frontend en de Open Trivia Database, waardoor de antwoorden verborgen blijven voor de gebruiker.

## Architectuur

- **Backend**: Java Spring Boot (Java 21)
- **Frontend**: Angular
- **Externe API**: Open Trivia Database API
- **Deployment**: Netlify (Frontend), Railway (Backend)

## Demo

https://quadquiz.netlify.app/


### Installatie

1. **Clone de repository**
   ```bash
   git clone https://github.com/NielSwusten/QuadQuiz.git
   cd QuadQuiz
   ```

2. **Backend opstarten**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
   
   De backend draait nu op `http://localhost:8080`

3. **Frontend opstarten**
   ```bash
   cd frontend
   npm install
   npm start
   ```
   
   De frontend is bereikbaar op `http://localhost:4200`

## Build Instructies

### Backend Build

```bash
# Ontwikkeling
cd backend
mvn spring-boot:run


```

### Frontend Build

```bash
# Ontwikkeling
cd frontend
npm run dev

```


### Tests
Het project bevat ook een aantal backend tests.

### Tests uitvoeren

```bash
cd backend
mvn test

```

<img width="525" height="281" alt="image" src="https://github.com/user-attachments/assets/4d040f32-46a1-45dd-8f74-b3d18183ce0f" />

<img width="510" height="236" alt="image" src="https://github.com/user-attachments/assets/8143c63c-f251-4e7a-b827-0a00319848f0" />


## API Documentatie

### Base URL
- **Ontwikkeling**: `http://localhost:8080/api`
- **Productie**: `https://backendquadquiz-production.up.railway.app/api`

### Endpoints

#### GET /questions
Haalt 10 trivia-vragen op.
<img width="1733" height="56" alt="image" src="https://github.com/user-attachments/assets/2665b7d9-55df-48f5-be5b-3ac41f7c6bc4" />

**Parameters:**
- `amount` (optional): Aantal vragen (standaard: 10)
- `category` (optional): Categorie ID
- `difficulty` (optional): easy, medium, hard (standaard: easy)

**Response:**

<img width="1734" height="793" alt="image" src="https://github.com/user-attachments/assets/2289c78e-7a27-4a5d-b6c0-7d4bd635b4d1" />


#### POST /checkanswers
Controleert de gegeven antwoorden.
<img width="1742" height="73" alt="image" src="https://github.com/user-attachments/assets/7f84dfb9-7b9e-4f4e-8142-4b4af400c605" />

**Request Body:**

<img width="721" height="289" alt="image" src="https://github.com/user-attachments/assets/496820bc-7ad4-4bc4-b178-84fa9e9b5b83" />


**Response:**

<img width="639" height="276" alt="image" src="https://github.com/user-attachments/assets/eecb6e17-7c37-47d5-b67d-c64887319fe7" />

