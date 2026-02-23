package com.himfg.hospitalinfantil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionWeb implements WebMvcConfigurer {

    private final InterceptorAutenticacion interceptorAutenticacion;

    @Autowired
    public ConfiguracionWeb(InterceptorAutenticacion interceptorAutenticacion) {
        this.interceptorAutenticacion = interceptorAutenticacion;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registroInterceptores) {
        registroInterceptores.addInterceptor(interceptorAutenticacion)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/autenticacion/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registroCors) {
        // Se habilita CORS para desarrollo local con frontend desacoplado.
        registroCors.addMapping("/api/**")
                .allowedOriginPatterns(
                        "http://localhost:*",
                        "http://127.0.0.1:*"
                )
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Auth-Token")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
