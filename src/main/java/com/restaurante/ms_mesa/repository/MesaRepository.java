package com.restaurante.ms_mesa.repository;

import com.restaurante.ms_mesa.entity.MesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity, UUID> {
    Optional<MesaEntity> findByNumero(int numero);
}
