package com.himfg.hospitalinfantil.config;

import com.himfg.hospitalinfantil.autenticacion.SesionActiva;
import com.himfg.hospitalinfantil.service.ServicioAutenticacion;
import com.himfg.hospitalinfantil.service.ServicioAutorizacionHttp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorAutenticacion implements HandlerInterceptor {

    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public InterceptorAutenticacion(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @Override
    public boolean preHandle(HttpServletRequest solicitudHttp, HttpServletResponse respuestaHttp, Object controlador) {
        if ("OPTIONS".equalsIgnoreCase(solicitudHttp.getMethod())) {
            return true;
        }

        if (!(controlador instanceof HandlerMethod)) {
            return true;
        }

        String token = solicitudHttp.getHeader("X-Auth-Token");
        SesionActiva sesionActiva = servicioAutenticacion.obtenerSesionPorToken(token);
        solicitudHttp.setAttribute(ServicioAutorizacionHttp.ATRIBUTO_SESION, sesionActiva);
        return true;
    }
}
