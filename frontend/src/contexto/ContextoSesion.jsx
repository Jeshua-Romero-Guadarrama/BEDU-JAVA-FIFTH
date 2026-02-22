import {createContext, useContext, useMemo, useState} from "react";
import {cerrarSesionRemota, iniciarSesion} from "../api/clienteApi";

const CLAVE_SESION = "sesion_himfg";
const ContextoSesion = createContext(null);

function leerSesionInicial() {
    const sesionSerializada = localStorage.getItem(CLAVE_SESION);
    if (!sesionSerializada) {
        return null;
    }
    try {
        return JSON.parse(sesionSerializada);
    } catch {
        localStorage.removeItem(CLAVE_SESION);
        return null;
    }
}

export function ProveedorSesion({children}) {
    const [sesion, setSesion] = useState(() => leerSesionInicial());

    const iniciar = async (usuario, clave) => {
        const respuestaInicioSesion = await iniciarSesion({usuario, clave});
        const nuevaSesion = {
            token: respuestaInicioSesion.token,
            usuario: respuestaInicioSesion.usuario,
            nombreVisible: respuestaInicioSesion.nombreVisible,
            rol: respuestaInicioSesion.rol,
            expiraEnSegundos: respuestaInicioSesion.expiraEnSegundos
        };

        localStorage.setItem(CLAVE_SESION, JSON.stringify(nuevaSesion));
        setSesion(nuevaSesion);
        return nuevaSesion;
    };

    const cerrar = async () => {
        const token = sesion?.token;
        if (token) {
            try {
                await cerrarSesionRemota(token);
            } catch {
                // Se prioriza cierre local aun cuando falle el backend.
            }
        }
        localStorage.removeItem(CLAVE_SESION);
        setSesion(null);
    };

    const limpiarPorSesionInvalida = () => {
        localStorage.removeItem(CLAVE_SESION);
        setSesion(null);
    };

    const valor = useMemo(
        () => ({
            sesion,
            estaAutenticado: Boolean(sesion?.token),
            iniciar,
            cerrar,
            limpiarPorSesionInvalida
        }),
        [sesion]
    );

    return <ContextoSesion.Provider value={valor}>{children}</ContextoSesion.Provider>;
}

export function useSesion() {
    const contexto = useContext(ContextoSesion);
    if (!contexto) {
        throw new Error("useSesion debe usarse dentro de ProveedorSesion");
    }
    return contexto;
}
