import {useEffect, useMemo, useState} from "react";
import {useNavigate} from "react-router-dom";
import {
    aplicarEncuesta,
    desactivarPaciente,
    listarCatalogoEncuestas,
    listarEncuestasPorPaciente,
    listarPacientes,
    obtenerUrlApiBase,
    registrarPaciente
} from "../api/clienteApi";
import {useSesion} from "../contexto/ContextoSesion";

function PaginaPanel() {
    const navegar = useNavigate();
    const {sesion, cerrar, limpiarPorSesionInvalida} = useSesion();
    const [catalogoEncuestas, setCatalogoEncuestas] = useState([]);
    const [indiceEncuesta, setIndiceEncuesta] = useState(0);
    const [pacientes, setPacientes] = useState([]);
    const [historialEncuestas, setHistorialEncuestas] = useState([]);
    const [alertas, setAlertas] = useState([]);

    const [formularioPaciente, setFormularioPaciente] = useState({
        nombreCompleto: "",
        fechaNacimiento: "",
        sexo: "",
        numeroExpediente: ""
    });

    const [formularioEncuesta, setFormularioEncuesta] = useState({
        pacienteId: "",
        dolor: "3",
        ansiedad: "3",
        dificultadRespiratoria: "3",
        observaciones: ""
    });

    const [identificadorHistorial, setIdentificadorHistorial] = useState("");

    const esAdministrador = sesion?.rol === "ADMINISTRADOR";
    const encuestaSeleccionada = catalogoEncuestas[indiceEncuesta] || null;
    const urlH2 = `${obtenerUrlApiBase()}/h2-console`;

    const resumenSesion = useMemo(() => {
        if (!sesion) {
            return "";
        }
        return `${sesion.nombreVisible} | Perfil: ${sesion.rol.replace("_", " ")}`;
    }, [sesion]);

    const agregarAlerta = (mensaje, tipo = "success") => {
        const id = crypto.randomUUID();
        setAlertas((previas) => [...previas, {id, mensaje, tipo}]);
        setTimeout(() => {
            setAlertas((previas) => previas.filter((alerta) => alerta.id !== id));
        }, 5000);
    };

    const manejarErrorApi = async (error) => {
        if (error?.estatus === 401) {
            limpiarPorSesionInvalida();
            navegar("/login", {replace: true});
            return;
        }
        agregarAlerta(error.message || "Ocurrio un error al procesar la solicitud.", "error");
    };

    const cargarDatosBase = async () => {
        try {
            const [catalogo, listaPacientes] = await Promise.all([
                listarCatalogoEncuestas(sesion.token),
                listarPacientes(sesion.token)
            ]);
            setCatalogoEncuestas(catalogo);
            setPacientes(listaPacientes);
        } catch (error) {
            await manejarErrorApi(error);
        }
    };

    useEffect(() => {
        // Se ejecuta la carga inicial del panel con catalogo y pacientes.
        if (!sesion?.token) {
            return;
        }
        cargarDatosBase();
    }, [sesion?.token]);

    const manejarCambioPaciente = (campo, valor) => {
        setFormularioPaciente((anterior) => ({...anterior, [campo]: valor}));
    };

    const manejarCambioEncuesta = (campo, valor) => {
        setFormularioEncuesta((anterior) => ({...anterior, [campo]: valor}));
    };

    const enviarPaciente = async (evento) => {
        evento.preventDefault();
        try {
            const pacienteCreado = await registrarPaciente(sesion.token, {
                nombreCompleto: formularioPaciente.nombreCompleto.trim(),
                fechaNacimiento: formularioPaciente.fechaNacimiento,
                sexo: formularioPaciente.sexo,
                numeroExpediente: formularioPaciente.numeroExpediente.trim()
            });
            agregarAlerta(`Paciente registrado: ${pacienteCreado.nombreCompleto} (ID ${pacienteCreado.id})`);
            setFormularioPaciente({
                nombreCompleto: "",
                fechaNacimiento: "",
                sexo: "",
                numeroExpediente: ""
            });
            const listaPacientes = await listarPacientes(sesion.token);
            setPacientes(listaPacientes);
        } catch (error) {
            await manejarErrorApi(error);
        }
    };

    const enviarEncuesta = async (evento) => {
        evento.preventDefault();
        if (!encuestaSeleccionada) {
            agregarAlerta("No se pudo identificar la encuesta seleccionada.", "error");
            return;
        }

        try {
            const encuestaGuardada = await aplicarEncuesta(sesion.token, {
                pacienteId: Number(formularioEncuesta.pacienteId),
                nombreEncuesta: encuestaSeleccionada.nombre,
                dolor: Number(formularioEncuesta.dolor),
                ansiedad: Number(formularioEncuesta.ansiedad),
                dificultadRespiratoria: Number(formularioEncuesta.dificultadRespiratoria),
                observaciones: formularioEncuesta.observaciones.trim()
            });
            agregarAlerta(`Encuesta registrada con id ${encuestaGuardada.id}`);
            setFormularioEncuesta({
                pacienteId: "",
                dolor: "3",
                ansiedad: "3",
                dificultadRespiratoria: "3",
                observaciones: ""
            });
        } catch (error) {
            await manejarErrorApi(error);
        }
    };

    const consultarHistorial = async (evento) => {
        evento.preventDefault();
        try {
            const encuestas = await listarEncuestasPorPaciente(sesion.token, Number(identificadorHistorial));
            setHistorialEncuestas(encuestas);
            if (encuestas.length === 0) {
                agregarAlerta("No hay encuestas registradas para el paciente indicado.", "error");
            }
        } catch (error) {
            await manejarErrorApi(error);
        }
    };

    const ejecutarDesactivacionPaciente = async (identificadorPaciente) => {
        try {
            await desactivarPaciente(sesion.token, identificadorPaciente);
            agregarAlerta(`Paciente ${identificadorPaciente} desactivado correctamente.`);
            const listaPacientes = await listarPacientes(sesion.token);
            setPacientes(listaPacientes);
        } catch (error) {
            await manejarErrorApi(error);
        }
    };

    const ejecutarCierreSesion = async () => {
        await cerrar();
        navegar("/login", {replace: true});
    };

    return (
        <div className="pagina">
            <div className="fondo-luz luz-uno"></div>
            <div className="fondo-luz luz-dos"></div>

            <header className="cabecera-panel">
                <div>
                    <p className="marca">HIMFG | Cuidados Paliativos Pediatricos</p>
                    <h1>Panel de gestion clinica</h1>
                    <p className="subtitulo">{resumenSesion}</p>
                </div>
                <div className="acciones-cabecera">
                    <a className="boton boton-secundario" href={urlH2} target="_blank" rel="noreferrer">
                        Consola de datos
                    </a>
                    <button type="button" className="boton boton-primario" onClick={ejecutarCierreSesion}>
                        Cerrar sesion
                    </button>
                </div>
            </header>

            <main className="layout-panel">
                <section className="tarjeta-panel">
                    <h2>Registrar paciente</h2>
                    {esAdministrador ? (
                        <form className="formulario-vertical" onSubmit={enviarPaciente}>
                            <label>
                                Nombre completo
                                <input
                                    value={formularioPaciente.nombreCompleto}
                                    onChange={(evento) => manejarCambioPaciente("nombreCompleto", evento.target.value)}
                                    required
                                    minLength={3}
                                    maxLength={120}
                                />
                            </label>
                            <label>
                                Fecha de nacimiento
                                <input
                                    type="date"
                                    value={formularioPaciente.fechaNacimiento}
                                    onChange={(evento) => manejarCambioPaciente("fechaNacimiento", evento.target.value)}
                                    required
                                />
                            </label>
                            <label>
                                Sexo
                                <select
                                    value={formularioPaciente.sexo}
                                    onChange={(evento) => manejarCambioPaciente("sexo", evento.target.value)}
                                    required
                                >
                                    <option value="">Seleccione una opcion</option>
                                    <option value="FEMENINO">Femenino</option>
                                    <option value="MASCULINO">Masculino</option>
                                    <option value="INTERSEXUAL">Intersexual</option>
                                    <option value="NO_ESPECIFICADO">No especificado</option>
                                </select>
                            </label>
                            <label>
                                Numero de expediente
                                <input
                                    value={formularioPaciente.numeroExpediente}
                                    onChange={(evento) => manejarCambioPaciente("numeroExpediente", evento.target.value)}
                                    required
                                    minLength={4}
                                    maxLength={30}
                                />
                            </label>
                            <button type="submit" className="boton boton-primario">Guardar paciente</button>
                        </form>
                    ) : (
                        <div className="estado-vacio">
                            Este perfil no tiene permisos para crear o desactivar pacientes.
                        </div>
                    )}
                </section>

                <section className="tarjeta-panel">
                    <h2>Aplicar encuesta</h2>
                    <form className="formulario-vertical" onSubmit={enviarEncuesta}>
                        <label>
                            ID de paciente
                            <input
                                type="number"
                                min="1"
                                value={formularioEncuesta.pacienteId}
                                onChange={(evento) => manejarCambioEncuesta("pacienteId", evento.target.value)}
                                required
                            />
                        </label>
                        <label>
                            Encuesta pediatrica
                            <select
                                value={indiceEncuesta}
                                onChange={(evento) => setIndiceEncuesta(Number(evento.target.value))}
                                required
                            >
                                {catalogoEncuestas.map((encuesta, indice) => (
                                    <option key={encuesta.nombre} value={indice}>{encuesta.nombre}</option>
                                ))}
                            </select>
                        </label>

                        <article className="tarjeta-interna">
                            {encuestaSeleccionada ? (
                                <>
                                    <h3>{encuestaSeleccionada.nombre}</h3>
                                    <p className="meta">{encuestaSeleccionada.descripcion}</p>
                                    <p className="meta">
                                        <strong>Grupo objetivo:</strong> {encuestaSeleccionada.grupoObjetivo}
                                    </p>
                                    <ul>
                                        {encuestaSeleccionada.focosEvaluacion.map((foco) => <li key={foco}>{foco}</li>)}
                                    </ul>
                                </>
                            ) : (
                                <p className="meta">No hay catalogo disponible.</p>
                            )}
                        </article>

                        <div className="rejilla-campos">
                            <label>
                                Dolor (1-5)
                                <input
                                    type="number"
                                    min="1"
                                    max="5"
                                    value={formularioEncuesta.dolor}
                                    onChange={(evento) => manejarCambioEncuesta("dolor", evento.target.value)}
                                    required
                                />
                            </label>
                            <label>
                                Ansiedad (1-5)
                                <input
                                    type="number"
                                    min="1"
                                    max="5"
                                    value={formularioEncuesta.ansiedad}
                                    onChange={(evento) => manejarCambioEncuesta("ansiedad", evento.target.value)}
                                    required
                                />
                            </label>
                            <label>
                                Dificultad respiratoria (1-5)
                                <input
                                    type="number"
                                    min="1"
                                    max="5"
                                    value={formularioEncuesta.dificultadRespiratoria}
                                    onChange={(evento) => manejarCambioEncuesta("dificultadRespiratoria", evento.target.value)}
                                    required
                                />
                            </label>
                        </div>

                        <label>
                            Observaciones clinicas
                            <textarea
                                rows="3"
                                minLength={4}
                                maxLength={500}
                                value={formularioEncuesta.observaciones}
                                onChange={(evento) => manejarCambioEncuesta("observaciones", evento.target.value)}
                                required
                            />
                        </label>
                        <button type="submit" className="boton boton-primario">Registrar encuesta</button>
                    </form>
                </section>

                <section className="tarjeta-panel panel-amplio">
                    <div className="encabezado-tarjeta">
                        <h2>Pacientes activos</h2>
                        <button type="button" className="boton boton-secundario" onClick={cargarDatosBase}>Actualizar</button>
                    </div>
                    <div className="rejilla-tarjetas">
                        {pacientes.length === 0 ? (
                            <div className="estado-vacio">No existen pacientes activos.</div>
                        ) : (
                            pacientes.map((paciente) => (
                                <article key={paciente.id} className="tarjeta-item">
                                    <h3>{paciente.nombreCompleto}</h3>
                                    <p className="meta">ID: {paciente.id}</p>
                                    <p className="meta">Edad: {paciente.edad} anos</p>
                                    <p className="meta">Sexo: {paciente.sexo}</p>
                                    <p className="meta">Expediente: {paciente.numeroExpediente}</p>
                                    <span className={`estado ${paciente.activo ? "activo" : ""}`}>
                                        {paciente.activo ? "Activo" : "Inactivo"}
                                    </span>
                                    {esAdministrador && paciente.activo && (
                                        <button
                                            type="button"
                                            className="boton boton-secundario"
                                            onClick={() => ejecutarDesactivacionPaciente(paciente.id)}
                                        >
                                            Desactivar
                                        </button>
                                    )}
                                </article>
                            ))
                        )}
                    </div>
                </section>

                <section className="tarjeta-panel panel-amplio">
                    <div className="encabezado-tarjeta">
                        <h2>Historial de encuestas</h2>
                        <form className="formulario-historial" onSubmit={consultarHistorial}>
                            <input
                                type="number"
                                min="1"
                                value={identificadorHistorial}
                                onChange={(evento) => setIdentificadorHistorial(evento.target.value)}
                                placeholder="ID de paciente"
                                required
                            />
                            <button type="submit" className="boton boton-secundario">Consultar</button>
                        </form>
                    </div>
                    <div className="rejilla-tarjetas">
                        {historialEncuestas.length === 0 ? (
                            <div className="estado-vacio">Se mostrara el historial despues de una consulta.</div>
                        ) : (
                            historialEncuestas.map((encuesta) => (
                                <article key={encuesta.id} className="tarjeta-item">
                                    <h3>{encuesta.nombreEncuesta}</h3>
                                    <p className="meta">Paciente: {encuesta.pacienteNombre} (ID {encuesta.pacienteId})</p>
                                    <p className="meta">Fecha: {new Date(encuesta.fechaAplicacion).toLocaleString()}</p>
                                    <p className="meta">
                                        Dolor: {encuesta.dolor} | Ansiedad: {encuesta.ansiedad} | Respiracion: {encuesta.dificultadRespiratoria}
                                    </p>
                                    <p className="meta">Promedio: {encuesta.promedioSintomas}</p>
                                    <p className="meta">{encuesta.observaciones}</p>
                                </article>
                            ))
                        )}
                    </div>
                </section>
            </main>

            <aside className="contenedor-alertas" aria-live="polite">
                {alertas.map((alerta) => (
                    <div key={alerta.id} className={`alerta ${alerta.tipo}`}>
                        {alerta.mensaje}
                    </div>
                ))}
            </aside>
        </div>
    );
}

export default PaginaPanel;
