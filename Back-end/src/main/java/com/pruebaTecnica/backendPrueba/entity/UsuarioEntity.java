package com.pruebaTecnica.backendPrueba.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.lang.Nullable;
@Entity
@Table(name = "PT_USUARIOS")
public class UsuarioEntity {
    @Id
    @Column(name = "ID")
    @Nullable
    private Integer id;

    @Column(name = "ID_ROL")
    @NotNull
    private Integer id_rol;

    @Column(name = "NOMBRE")
    @NotNull
    private String nombre;

    @Column(name = "FECHA_CREACION")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
    @NotNull
    private Date fecha_creacion;

    @Column(name = "ACTIVO")
    @Pattern(regexp = "(S|s|N|n)", message = "Este campo solo permite los valores S, s, N, n")
    @NotNull
    private String activo;

    public UsuarioEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
}
