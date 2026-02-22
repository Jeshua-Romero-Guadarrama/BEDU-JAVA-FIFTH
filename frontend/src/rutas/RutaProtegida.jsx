import {Navigate, useLocation} from "react-router-dom";
import {useSesion} from "../contexto/ContextoSesion";

function RutaProtegida({children}) {
    const {estaAutenticado} = useSesion();
    const ubicacion = useLocation();

    if (!estaAutenticado) {
        return <Navigate to="/login" replace state={{desde: ubicacion}}/>;
    }

    return children;
}

export default RutaProtegida;
