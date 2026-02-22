# Backend - HIMFG Cuidados Paliativos

API Spring Boot con arquitectura en N capas:

- `controller`
- `service`
- `repository`
- `domain`

## Requisitos

- Java 17 o superior

## Comandos

```powershell
.\gradlew.bat clean build
.\gradlew.bat bootRun
```

## Documentacion de API (Swagger)

Con el backend levantado, la documentacion se consulta en:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs` (JSON OpenAPI)

## Endpoints principales

### Autenticacion

- `POST /api/autenticacion/iniciar-sesion`
- `GET /api/autenticacion/perfil`
- `POST /api/autenticacion/cerrar-sesion`

### Pacientes

- `GET /api/pacientes`
- `GET /api/pacientes/{id}`
- `POST /api/pacientes` (solo Administrador)
- `PATCH /api/pacientes/{id}/desactivar` (solo Administrador)

### Encuestas

- `GET /api/encuestas/catalogo`
- `POST /api/encuestas`
- `GET /api/encuestas?pacienteId={id}`

## Base de datos local

- URL JDBC: `jdbc:h2:file:./base-datos/himfgdb;AUTO_SERVER=TRUE`
- Consola: `http://localhost:8080/h2-console`
