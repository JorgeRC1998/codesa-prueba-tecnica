package com.pruebaTecnica.backendPrueba.controllers;

import javax.validation.Valid;

import com.pruebaTecnica.backendPrueba.dto.RespuestaDto;
import com.pruebaTecnica.backendPrueba.dto.UsuarioDto;
import com.pruebaTecnica.backendPrueba.entity.UsuarioEntity;
import com.pruebaTecnica.backendPrueba.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class UsuarioController {
    
    @Autowired
    UsuarioService usuarioService;

    /**
     * Realiza la consulta de roles disponibles
     *
     * @return Listado con los roles
     * @author JorgeRojas
     */
    @GetMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> obtenerRoles(){
        RespuestaDto respuestaDto = usuarioService.obtenerRoles();
        return new ResponseEntity<Object>(respuestaDto.getData(), HttpStatus.valueOf(respuestaDto.getStatus()));
    }

    /**
     * Realiza la consulta de usuarios
     *
     * @param usuarioDto Parámetros para realizar la consulta
     * @return Listado con los usuarios que cumplen el filtro
     * @author JorgeRojas
     */
    @PostMapping(path = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> obtenerUsuarios(@RequestBody  @Valid UsuarioDto usuarioDto){
        RespuestaDto respuestaDto = usuarioService.obtenerUsuarios(usuarioDto);
        return new ResponseEntity<Object>(respuestaDto.getData(), HttpStatus.valueOf(respuestaDto.getStatus()));
    }

    /**
     * Inserta un nuevo usuario
     *
     * @param usuarioEntity Parámetros para realizar la inserción del nuevo usuario
     * @return Resultado de la operación
     * @author JorgeRojas
     */
    @PostMapping(path = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertarUsuario(@RequestBody  @Valid UsuarioEntity usuarioEntity){
        RespuestaDto respuestaDto = usuarioService.insertarNuevoUsuario(usuarioEntity);
        return new ResponseEntity<Object>(respuestaDto.getData(), HttpStatus.valueOf(respuestaDto.getStatus()));
    }

    /**
     * Actualiza un usuario
     *
     * @param usuarioEntity Parámetros para realizar la actualización del nuevo usuario
     * @return Resultado de la operación
     * @author JorgeRojas
     */
    @PutMapping(path = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> actualizarUsuario(@RequestBody  @Valid UsuarioEntity usuarioEntity){
        RespuestaDto respuestaDto = usuarioService.actualizarUsuario(usuarioEntity);
        return new ResponseEntity<Object>(respuestaDto.getData(), HttpStatus.valueOf(respuestaDto.getStatus()));
    }

    /**
     * Elimina un usuario
     *
     * @param idUsuario Id del usuario a eliminar
     * @return Resultado de la operación
     * @author JorgeRojas
     */
    @DeleteMapping(path = "/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> actualizarUsuario(@PathVariable("idUsuario") Integer idUsuario){
        RespuestaDto respuestaDto = usuarioService.eliminarUsuario(idUsuario);
        return new ResponseEntity<Object>(respuestaDto.getData(), HttpStatus.valueOf(respuestaDto.getStatus()));
    }

}
