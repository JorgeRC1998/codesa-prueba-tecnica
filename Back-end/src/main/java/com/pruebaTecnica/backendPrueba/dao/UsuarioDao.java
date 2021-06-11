package com.pruebaTecnica.backendPrueba.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.pruebaTecnica.backendPrueba.entity.UsuarioEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<UsuarioEntity, Long> {
    @Query(value="SELECT seq_usuarios.NEXTVAL from dual", nativeQuery=true)
    Integer obtenerIdUsuario();

    @Query("SELECT u FROM UsuarioEntity u WHERE NOMBRE = :nombre")
    List<UsuarioEntity> findUserByName(@Param("nombre") String nombre);

    @Query("SELECT u FROM UsuarioEntity u WHERE ID = :id")
    List<UsuarioEntity> findUserById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO PT_USUARIOS VALUES (:id, :id_rol, :nombre, :fecha_creacion, :activo)", nativeQuery = true)
    @Transactional
    void insertarUsuario(@Param("id") Integer id,
                        @Param("id_rol") Integer id_rol,
                        @Param("nombre") String nombre,
                        @Param("fecha_creacion") Date fecha_creacion,
                        @Param("activo") String activo);

    @Modifying
    @Query(value = "UPDATE PT_USUARIOS SET ID_ROL = :id_rol, NOMBRE = :nombre, ACTIVO = :activo WHERE id = :id", nativeQuery = true)
    @Transactional
    void actualizarUsuario(@Param("id") Integer id,
                        @Param("id_rol") Integer id_rol,
                        @Param("nombre") String nombre,
                        @Param("activo") String activo);

    @Modifying
    @Query(value = "DELETE FROM PT_USUARIOS WHERE id = :id", nativeQuery = true)
    @Transactional
    void eliminarUsuario(@Param("id") Integer id);
}
