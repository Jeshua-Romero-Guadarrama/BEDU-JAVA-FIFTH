import {Link} from "react-router-dom";

function PaginaInicio() {
    return (
        <div className="pagina">
            <div className="fondo-luz luz-uno"></div>
            <div className="fondo-luz luz-dos"></div>

            <header className="cabecera-principal">
                <div>
                    <p className="marca">Hospital Infantil de Mexico Federico Gomez</p>
                    <h1>Plataforma Clinica de Cuidados Paliativos Pediatricos</h1>
                    <p className="subtitulo">
                        Sistema institucional para registro de pacientes, aplicacion de encuestas clinicas y seguimiento
                        continuo de sintomas.
                    </p>
                </div>
                <div className="acciones-cabecera">
                    <Link to="/login" className="boton boton-primario">Ingresar al sistema</Link>
                </div>
            </header>

            <main className="contenido-inicio">
                <section className="tarjeta-destacada">
                    <h2>Objetivo institucional</h2>
                    <p>
                        Se consolida la valoracion de pacientes pediatricos en un flujo digital con trazabilidad y criterios
                        unificados para el equipo asistencial.
                    </p>
                </section>

                <section className="rejilla-funciones">
                    <article className="tarjeta-funcion">
                        <h3>Gestion de pacientes</h3>
                        <p>Se administra expediente, estado activo y datos clinicos base con control de roles.</p>
                    </article>
                    <article className="tarjeta-funcion">
                        <h3>Encuestas clinicas</h3>
                        <p>Se aplican tres instrumentos pediatricos con historial estructurado por paciente.</p>
                    </article>
                    <article className="tarjeta-funcion">
                        <h3>Trazabilidad operativa</h3>
                        <p>Se conserva evidencia de seguimiento para toma de decisiones y continuidad del cuidado.</p>
                    </article>
                </section>
            </main>
        </div>
    );
}

export default PaginaInicio;
