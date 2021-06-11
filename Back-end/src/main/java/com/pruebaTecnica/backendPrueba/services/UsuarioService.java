package com.pruebaTecnica.backendPrueba.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pruebaTecnica.backendPrueba.dao.RolDao;
import com.pruebaTecnica.backendPrueba.dao.UsuarioDao;
import com.pruebaTecnica.backendPrueba.dto.RespuestaDto;
import com.pruebaTecnica.backendPrueba.dto.UsuarioDto;
import com.pruebaTecnica.backendPrueba.entity.RolEntity;
import com.pruebaTecnica.backendPrueba.entity.UsuarioEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    EntityManager entityManager;
    @Autowired
    RolDao rolDao;
    @Autowired
    UsuarioDao usuarioDao;

    List<RolEntity>roles = new ArrayList<RolEntity>();
    List<UsuarioEntity>usuarios = new ArrayList<UsuarioEntity>();
    RespuestaDto respuestaDto = new RespuestaDto();
    Map<String, Object> errores = new HashMap<>();
    Map<String, Object> respuesta = new HashMap<>();

    public RespuestaDto obtenerRoles(){
        try{
            roles = rolDao.findAll();
            respuestaDto.setData(roles);
            respuestaDto.setStatus(200);
        }catch(Exception e){
            errores.put("mensaje", "Excepción obteniendo listado de roles: " + e.getMessage());
            errores.put("detalle", e.getCause().getCause().toString());
            respuestaDto.setData(errores);
            respuestaDto.setStatus(500);
            // TODO logger aquí
        }
        return respuestaDto;
    }

    public RespuestaDto obtenerUsuarios(UsuarioDto usuarioDto){
        try{
            String queryIni = "SELECT u FROM UsuarioEntity u WHERE";
            if(!(usuarioDto.getIdRol() == null)){
                queryIni +=  " u.id_rol = " + usuarioDto.getIdRol();
            }
            if(!(usuarioDto.getNombre() == null)){
                queryIni +=  " AND u.nombre like " + "'%" + usuarioDto.getNombre() + "%'";
            }
            if(usuarioDto.getIdRol() == null && usuarioDto.getNombre() == null){
                queryIni = queryIni.replace("WHERE", "");
            }
            queryIni = queryIni.replace("WHERE AND", "WHERE");
            Query usuariosQuery = entityManager.createQuery(queryIni);
            List<UsuarioEntity> usuarios = usuariosQuery.getResultList();
            respuestaDto.setData(usuarios);
            respuestaDto.setStatus(200);
        }catch(Exception e){
            errores.put("mensaje", "Excepción obteniendo listado de usuarios: " + e.getMessage());
            errores.put("detalle", e.getCause().getCause().toString());
            respuestaDto.setData(errores);
            respuestaDto.setStatus(500);
            // TODO logger aquí
        }
        return respuestaDto;
    }

    public RespuestaDto insertarNuevoUsuario(UsuarioEntity usuario){
        try{
            // Verificar usuario unico (esto se debería hacer con un constraint por BD)
            List<UsuarioEntity> usuarioEntity = usuarioDao.findUserByName(usuario.getNombre());
            if(usuarioEntity.size() > 0){
                respuesta.put("codigo", 1);
                respuesta.put("mensaje", "Usuario a crear ya existe");
                respuestaDto.setData(respuesta);
                respuestaDto.setStatus(400);
            }else{
                Integer idUsuario = usuarioDao.obtenerIdUsuario();
                usuarioDao.insertarUsuario(
                    idUsuario,
                    usuario.getId_rol(),
                    usuario.getNombre(),
                    usuario.getFecha_creacion(),
                    usuario.getActivo()
                );
                respuesta.put("codigo", 0);
                respuesta.put("mensaje", "Operacion se completo con exito");
                respuestaDto.setData(respuesta);
                respuestaDto.setStatus(200);
            }
        }catch(Exception e){
            respuesta.put("codigo", 2);
            errores.put("mensaje", "Excepción insertando usuario: " + e.getMessage());
            errores.put("detalle", e.getCause().getCause().toString());
            respuestaDto.setData(errores);
            respuestaDto.setStatus(500);
            // TODO logger aquí
        }
        return respuestaDto;
    }

    public RespuestaDto actualizarUsuario(UsuarioEntity usuario){
        try{
            // Verificar si usuario existe (esto igual quedaría mejor con un SP en BD)
            List<UsuarioEntity> usuarioEntity = usuarioDao.findUserById(usuario.getId());
            if(usuarioEntity.size() == 0){
                respuesta.put("codigo", 1);
                respuesta.put("mensaje", "Usuario a actualizar no existe");
                respuestaDto.setData(respuesta);
                respuestaDto.setStatus(400);
            }else{
                usuarioDao.actualizarUsuario(
                    usuario.getId(),
                    usuario.getId_rol(),
                    usuario.getNombre(),
                    usuario.getActivo()
                );
                respuesta.put("codigo", 0);
                respuesta.put("mensaje", "Operacion se completo con exito");
                respuestaDto.setData(respuesta);
                respuestaDto.setStatus(200);
            }
        }catch(Exception e){
            respuesta.put("codigo", 2);
            errores.put("mensaje", "Excepción actualizando usuario: " + e.getMessage());
            errores.put("detalle", e.getCause().getCause().toString());
            respuestaDto.setData(errores);
            respuestaDto.setStatus(500);
            // TODO logger aquí
        }
        return respuestaDto;
    }
    
    public RespuestaDto eliminarUsuario(Integer idUsuario){
        try{
            // Verificar si usuario existe (esto igual quedaría mejor con un SP en BD)
            List<UsuarioEntity> usuarioEntity = usuarioDao.findUserById(idUsuario);
            if(usuarioEntity.size() == 0){
                respuesta.put("codigo", 1);
                respuesta.put("mensaje", "Usuario a eliminar no existe");
                respuestaDto.setData(respuesta);
                respuestaDto.setStatus(400);
            }else{
                usuarioDao.eliminarUsuario(idUsuario);
                respuesta.put("codigo", 0);
                respuesta.put("mensaje", "Operacion se completo con exito");
                respuestaDto.setData(respuesta);
                respuestaDto.setStatus(200);
            }
        }catch(Exception e){
            respuesta.put("codigo", 2);
            errores.put("mensaje", "Excepción eliminando usuario: " + e.getMessage());
            errores.put("detalle", e.getCause().getCause().toString());
            respuestaDto.setData(errores);
            respuestaDto.setStatus(500);
            // TODO logger aquí
        }
        return respuestaDto;
    }

}
