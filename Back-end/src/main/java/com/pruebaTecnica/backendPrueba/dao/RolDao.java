package com.pruebaTecnica.backendPrueba.dao;

import java.util.List;

import com.pruebaTecnica.backendPrueba.entity.RolEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolDao extends JpaRepository<RolEntity, Long> {
    List<RolEntity> findAll();
}
