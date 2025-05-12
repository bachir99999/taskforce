# 🛠️ TaskForce - Gestion de Tâches Collaboratives

TaskForce est une application web RESTful développée avec **Spring Boot**, permettant aux utilisateurs de s'enregistrer, de se connecter et de gérer des tâches assignées. L'application intègre une authentification sécurisée par JWT.

---

## ⚙️ Fonctionnalités principales

-  Inscription d’un utilisateur
-  Authentification via JWT (JSON Web Token)
-  Création, consultation et gestion de tâches
-  Attribution de tâches à des utilisateurs
-  Filtrage des tâches par utilisateur

---

## 🔐 Authentification

L’authentification repose sur un système **JWT Bearer Token** :

- `/auth/register` : inscription
- `/auth/login` : connexion, retourne un token
- `/auth/verify` : vérifie la validité d’un token

---

## 📁 Structure de l’API

### 🔐 AuthController

| Méthode | Endpoint         | Description                     |
|--------|------------------|---------------------------------|
| POST   | `/auth/register` | Enregistrement d'un utilisateur |
| POST   | `/auth/login`    | Authentification + token JWT    |
| POST   | `/auth/verify`   | Vérification du token JWT       |

### 👤 UserController

| Méthode | Endpoint        | Description                       |
|--------|-----------------|-----------------------------------|
| GET    | `/users`           | Détails d'un user       |
| POST   | `/users`           | Création d'une tâche             |
| GET    | `/users/{id}`      | Liste de toutes les tâches       |
| GET    | `/users/{id}/tasks`|Liste des tâches d’un utilisateur |
| PUT    | `/users/{id}`      | Mise à jour d'un user          |
| DELETE | `/users/{id}`      | Suppression d'un user          |

### 📋 TaskController

| Méthode | Endpoint     | Description              |
|--------|--------------|--------------------------|
| GET    | `/tasks`     | Liste de toutes les tâches |
| POST   | `/tasks`     | Création d'une tâche      |
| GET    | `/tasks/{id}`| Détails d'une tâche       |
| PUT    | `/tasks/{id}`| Mise à jour d'une tâche   |
| DELETE | `/tasks/{id}`| Suppression d'une tâche   |

---

## 🧱 Modèle de données

### User

- `id`: Integer
- `name`: String
- `email`: String
- `password`: String (crypté)
- `taskList`: List<Task>

### Task

- `id`: Integer
- `name`: String
- `description`: String
- `dueDate`: LocalDate
- `status`: Enum (TODO, IN_PROGRESS, DONE)
- `assignedTo`: User

---

## 🔐 Sécurité

L'application utilise **Spring Security** avec un filtre JWT personnalisé.

- Authentification via `AuthenticationManager`
- Génération et vérification du token dans `JWTService`
- `OncePerRequestFilter` pour valider le token à chaque requête

---

## 🧪 Tests

- Utilisation de **JUnit 5** et **Mockito** pour les tests unitaires.
- Test des services, contrôleurs, et authentification.

---

## 🛠️ Technologies utilisées

- Java 23
- Spring Boot 3
- Spring Security
- JWT
- PostgreSQL
- Hibernate / JPA
- Lombok (optionnel)
- Maven

---

## 📦 Exemple de requête `POST /tasks`

```json
{
  "name": "Write documentation",
  "description": "Create the README.md",
  "dueDate": "2025-05-01",
  "status": "TODO",
  "assignedToId": 1
}
