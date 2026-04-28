package com.restaurante.ms_mesa.repository;

import com.restaurante.ms_mesa.entity.MesaEntity;
import com.restaurante.ms_mesa.enumeration.StatusMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity, UUID> {
    Optional<MesaEntity> findByNumero(int numero);

    @Query("""
    SELECT m FROM MesaEntity m
    WHERE (:status IS NULL OR m.status = :status)
    AND (:capacidade IS NULL OR m.capacidade = :capacidade)
    """)
    List<MesaEntity> findByFilters(@Param("status") StatusMesa status,
                                   @Param("capacidade") Integer capacidade);


}
