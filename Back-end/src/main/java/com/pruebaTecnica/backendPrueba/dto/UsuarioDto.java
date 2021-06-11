package com.pruebaTecnica.backendPrueba.dto;

import org.springframework.lang.Nullable;

public class UsuarioDto {
    @Nullable
    private Integer idRol;
    @Nullable
    private String nombre;

    public UsuarioDto() {}

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
