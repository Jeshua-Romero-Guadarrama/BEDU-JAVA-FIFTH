# Frontend React - HIMFG Cuidados Paliativos

Frontend implementado con React + Vite, desacoplado del backend Java.

## Estructura principal

- `src/api`: cliente HTTP y funciones de acceso a la API.
- `src/contexto`: estado global de sesion/autenticacion.
- `src/paginas`: vistas (Inicio, Login y Panel).
- `src/rutas`: proteccion de rutas privadas.
- `src/estilos`: estilos globales.

## Requisitos

- Node.js 18+ (recomendado 20+)

## Comandos

Instalacion de dependencias:

```powershell
npm install
```

Desarrollo:

```powershell
npm run dev
```

Build de produccion:

```powershell
npm run build
```

Vista previa del build:

```powershell
npm run preview
```

## URL de la API

Por defecto se usa:

- `http://localhost:8080`

Si se requiere otra URL, crear archivo `.env`:

```env
VITE_URL_API=http://localhost:8080
```
