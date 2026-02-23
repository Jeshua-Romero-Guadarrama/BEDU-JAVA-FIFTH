package com.himfg.hospitalinfantil.service;

import com.himfg.hospitalinfantil.autenticacion.RolUsuario;
import com.himfg.hospitalinfantil.autenticacion.SesionActiva;
import com.himfg.hospitalinfantil.exception.ExcepcionNoAutorizado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutorizacionHttp {

    public static final String ATRIBUTO_SESION = "sesion_himfg";

    public SesionActiva obtenerSesion(HttpServletRequest solicitudHttp) {
        Object sesion = solicitudHttp.getAttribute(ATRIBUTO_SESION);
        if (sesion instanceof SesionActiva sesionActiva) {
            return sesionActiva;
        }
        throw new ExcepcionNoAutorizado("No existe una sesion valida en la solicitud");
    }

    public void exigirRol(HttpServletRequest solicitudHttp, RolUsuario rolRequerido) {
        SesionActiva sesionActiva = obtenerSesion(solicitudHttp);
        if (sesionActiva.rol() != rolRequerido) {
            throw new ExcepcionNoAutorizado("No tiene permisos para realizar esta operacion");
        }
    }
}
