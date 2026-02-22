# BEDU-JAVA-FIFTH (Backend Java + Frontend React)

Proyecto organizado con estructura profesional separada:

- `backend/`: API Java con Spring Boot, JPA, H2 y Swagger.
- `frontend/`: SPA React con Vite y rutas protegidas por sesion.
- `src/com/`: codigo historico de consola.

## 1) Ejecutar backend (Java)

```powershell
cd backend
.\gradlew.bat clean build
.\gradlew.bat bootRun
```

Recursos backend:

- API base: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 console: `http://localhost:8080/h2-console`

## 2) Ejecutar frontend (React)

En otra terminal:

```powershell
cd frontend
npm install
npm run dev
```

Frontend:

- `http://localhost:5500`

## 3) Credenciales demo

- Administrador
  - Usuario: `admin_himfg`
  - Clave: `AdminHIMFG2026!`

- Medico Pasante
  - Usuario: `pasante_himfg`
  - Clave: `PasanteHIMFG2026!`

## 4) Nota tecnica

El frontend consume la API desde `VITE_URL_API`, con valor por defecto:

- `http://localhost:8080`

El backend ya tiene CORS habilitado para `localhost` y `127.0.0.1` en desarrollo.
