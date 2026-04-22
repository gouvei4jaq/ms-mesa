package com.restaurante.ms_mesa.repository;

import com.restaurante.ms_mesa.entity.MesaEntity;
import com.restaurante.ms_mesa.enumeration.StatusMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity, UUID> {
    Optional<MesaEntity> findByNumero(int numero);

    List<MesaEntity> findByStatusAndCapacidade(StatusMesa status, int capacidade);

    List<MesaEntity> findByStatus(StatusMesa status);

    List<MesaEntity> findByCapacidade(Integer capacidade);
}
