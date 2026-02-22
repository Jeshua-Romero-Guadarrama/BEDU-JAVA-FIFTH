import {useEffect, useState} from "react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {useSesion} from "../contexto/ContextoSesion";

function PaginaLogin() {
    const navegar = useNavigate();
    const ubicacion = useLocation();
    const {iniciar, estaAutenticado} = useSesion();

    const [usuario, setUsuario] = useState("");
    const [clave, setClave] = useState("");
    const [enviando, setEnviando] = useState(false);
    const [mensajeError, setMensajeError] = useState("");

    useEffect(() => {
        if (estaAutenticado) {
            navegar("/panel", {replace: true});
        }
    }, [estaAutenticado, navegar]);

    const destino = ubicacion.state?.desde?.pathname || "/panel";

    const manejarEnvio = async (evento) => {
        evento.preventDefault();
        setEnviando(true);
        setMensajeError("");

        try {
            await iniciar(usuario.trim(), clave);
            navegar(destino, {replace: true});
        } catch (error) {
            setMensajeError(error.message || "No se pudo iniciar sesion.");
        } finally {
            setEnviando(false);
        }
    };

    return (
        <div className="pagina">
            <div className="fondo-luz luz-uno"></div>
            <div className="fondo-luz luz-dos"></div>

            <main className="contenedor-login">
                <section className="tarjeta-login">
                    <Link className="enlace-regresar" to="/">Volver al inicio</Link>
                    <h1>Inicio de sesion</h1>
                    <p className="subtitulo">
                        Acceso para personal autorizado del servicio de cuidados paliativos pediatricos.
                    </p>

                    <form className="formulario-vertical" onSubmit={manejarEnvio}>
                        <label>
                            Usuario
                            <input
                                value={usuario}
                                onChange={(evento) => setUsuario(evento.target.value)}
                                name="usuario"
                                type="text"
                                required
                                autoComplete="username"
                            />
                        </label>
                        <label>
                            Clave
                            <input
                                value={clave}
                                onChange={(evento) => setClave(evento.target.value)}
                                name="clave"
                                type="password"
                                required
                                autoComplete="current-password"
                            />
                        </label>
                        <button type="submit" className="boton boton-primario" disabled={enviando}>
                            {enviando ? "Validando..." : "Entrar"}
                        </button>
                    </form>

                    {mensajeError && <div className="mensaje-login error">{mensajeError}</div>}
                </section>

                <section className="tarjeta-credenciales">
                    <h2>Credenciales demo</h2>
                    <article>
                        <h3>Administrador</h3>
                        <p><strong>Usuario:</strong> admin_himfg</p>
                        <p><strong>Clave:</strong> AdminHIMFG2026!</p>
                    </article>
                    <article>
                        <h3>Medico Pasante</h3>
                        <p><strong>Usuario:</strong> pasante_himfg</p>
                        <p><strong>Clave:</strong> PasanteHIMFG2026!</p>
                    </article>
                </section>
            </main>
        </div>
    );
}

export default PaginaLogin;
