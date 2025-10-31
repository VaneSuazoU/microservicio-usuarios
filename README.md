# README · Microservicio Usuarios

## 📌 Descripción
Microservicio encargado de **gestionar usuarios** y realizar **autenticación básica** mediante un endpoint de login.  
Incluye operaciones CRUD completas sobre la entidad `User` y un método adicional para validar credenciales.

## 🧱 Stack
- Java 17 · Spring Boot 3.x
- Spring Web · Spring Data JPA
- SQLite (archivo `.db`)
- Maven

## 📚 Endpoints disponibles

> **Ruta base:** `/api/users`

| Método | Ruta              | Body (JSON)                                                  | Descripción                |
|-------:|-------------------|--------------------------------------------------------------|----------------------------|
| GET    | `/api/users`      | —                                                            | Listar todos los usuarios  |
| GET    | `/api/users/{id}` | —                                                            | Obtener usuario por ID     |
| POST   | `/api/users`      | `{ "username": "...", "password": "...", "role": "..." }`   | Crear usuario nuevo        |
| PUT    | `/api/users/{id}` | `{ "username": "...", "password": "...", "role": "..." }`   | Actualizar usuario         |
| DELETE | `/api/users/{id}` | —                                                            | Eliminar usuario           |
| POST   | `/api/users/login`| `{ "username": "...", "password": "..." }`                  | Validar credenciales y devuelve texto con el rol o error |

### Ejemplo de esquema `User`
```json
{
  "id": 1,
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

## ▶️ Ejecución
```bash
mvn clean spring-boot:run
# o
./gradlew bootRun
```
API disponible en `http://localhost:8081`

## 🧪 Pruebas en Postman o curl

### Crear usuario
```bash
curl -X POST http://localhost:8081/api/users   -H "Content-Type: application/json"   -d '{ "username": "admin", "password": "admin123", "role": "ADMIN" }'
```

### Listar usuarios
```bash
curl http://localhost:8081/api/users
```

### Obtener usuario por ID
```bash
curl http://localhost:8081/api/users/1
```

### Actualizar usuario
```bash
curl -X PUT http://localhost:8081/api/users/1   -H "Content-Type: application/json"   -d '{ "username": "admin", "password": "nuevo123", "role": "ADMIN" }'
```

### Eliminar usuario
```bash
curl -X DELETE http://localhost:8081/api/users/1
```

### Login
```bash
curl -X POST http://localhost:8081/api/users/login   -H "Content-Type: application/json"   -d '{ "username": "admin", "password": "admin123" }'
```
> Devuelve `OK_ROLE=ADMIN` si las credenciales son válidas, o `401 Unauthorized` si no.

