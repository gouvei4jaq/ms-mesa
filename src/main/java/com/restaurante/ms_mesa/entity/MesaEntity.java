package com.restaurante.ms_mesa.entity;

import com.restaurante.ms_mesa.enumeration.StatusMesa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesa")
public class MesaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private UUID id;

    @Column(name = "numero")
    private Integer numero;

    @Column
    private StatusMesa status;

    @Column
    private Integer capacidade;

    @Column(name = "data_de_atualizacao")
    private LocalDateTime dataDeAtualizacao;

}
