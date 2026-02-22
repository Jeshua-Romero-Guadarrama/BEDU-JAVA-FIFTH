import {Navigate, Route, Routes} from "react-router-dom";
import PaginaInicio from "./paginas/PaginaInicio";
import PaginaLogin from "./paginas/PaginaLogin";
import PaginaPanel from "./paginas/PaginaPanel";
import RutaProtegida from "./rutas/RutaProtegida";

function App() {
    return (
        <Routes>
            <Route path="/" element={<PaginaInicio/>}/>
            <Route path="/login" element={<PaginaLogin/>}/>
            <Route
                path="/panel"
                element={
                    <RutaProtegida>
                        <PaginaPanel/>
                    </RutaProtegida>
                }
            />
            <Route path="*" element={<Navigate to="/" replace/>}/>
        </Routes>
    );
}

export default App;
