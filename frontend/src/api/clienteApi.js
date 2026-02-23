const URL_API_BASE = (import.meta.env.VITE_URL_API || "http://localhost:8080").replace(/\/$/, "");

async function solicitarApi(ruta, opciones = {}) {
    const respuesta = await fetch(`${URL_API_BASE}${ruta}`, opciones);
    const tipoContenido = respuesta.headers.get("content-type") || "";
    const esJson = tipoContenido.includes("application/json");
    const datos = esJson ? await respuesta.json() : await respuesta.text();

    if (!respuesta.ok) {
        const error = new Error(construirMensajeError(datos, respuesta.status));
        error.estatus = respuesta.status;
        error.detalle = datos;
        throw error;
    }

    return datos;
}

function construirMensajeError(datos, estatus) {
    if (datos && typeof datos === "object") {
        const mensajeBase = datos.mensaje || `Error HTTP ${estatus}`;
        if (datos.erroresPorCampo) {
            const detalleCampos = Object.entries(datos.erroresPorCampo)
                .map(([campo, errorCampo]) => `${campo}: ${errorCampo}`)
                .join("\n");
            return `${mensajeBase}\n${detalleCampos}`;
        }
        return mensajeBase;
    }

    return typeof datos === "string" && datos.trim() !== ""
        ? datos
        : `Error HTTP ${estatus}`;
}

function construirCabeceras(tokenSesion, incluirJson = true) {
    const cabeceras = {};
    if (incluirJson) {
        cabeceras["Content-Type"] = "application/json";
    }
    if (tokenSesion) {
        cabeceras["X-Auth-Token"] = tokenSesion;
    }
    return cabeceras;
}

export function iniciarSesion(solicitud) {
    return solicitarApi("/api/autenticacion/iniciar-sesion", {
        method: "POST",
        headers: construirCabeceras(null, true),
        body: JSON.stringify(solicitud)
    });
}

export function consultarPerfil(tokenSesion) {
    return solicitarApi("/api/autenticacion/perfil", {
        method: "GET",
        headers: construirCabeceras(tokenSesion, false)
    });
}

export function cerrarSesionRemota(tokenSesion) {
    return solicitarApi("/api/autenticacion/cerrar-sesion", {
        method: "POST",
        headers: construirCabeceras(tokenSesion, false)
    });
}

export function listarPacientes(tokenSesion) {
    return solicitarApi("/api/pacientes", {
        method: "GET",
        headers: construirCabeceras(tokenSesion, false)
    });
}

export function registrarPaciente(tokenSesion, solicitud) {
    return solicitarApi("/api/pacientes", {
        method: "POST",
        headers: construirCabeceras(tokenSesion, true),
        body: JSON.stringify(solicitud)
    });
}

export function desactivarPaciente(tokenSesion, identificadorPaciente) {
    return solicitarApi(`/api/pacientes/${identificadorPaciente}/desactivar`, {
        method: "PATCH",
        headers: construirCabeceras(tokenSesion, false)
    });
}

export function listarCatalogoEncuestas(tokenSesion) {
    return solicitarApi("/api/encuestas/catalogo", {
        method: "GET",
        headers: construirCabeceras(tokenSesion, false)
    });
}

export function aplicarEncuesta(tokenSesion, solicitud) {
    return solicitarApi("/api/encuestas", {
        method: "POST",
        headers: construirCabeceras(tokenSesion, true),
        body: JSON.stringify(solicitud)
    });
}

export function listarEncuestasPorPaciente(tokenSesion, identificadorPaciente) {
    return solicitarApi(`/api/encuestas?pacienteId=${identificadorPaciente}`, {
        method: "GET",
        headers: construirCabeceras(tokenSesion, false)
    });
}

export function obtenerUrlApiBase() {
    return URL_API_BASE;
}
