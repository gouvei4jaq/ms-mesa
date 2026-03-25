package com.restaurante.ms_mesa.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "mesa")
public class MesaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private UUID id;
    @Column(name = "numero")
    private Integer number;
    private String status;
    @Column(name = "data_de_atualizacao")
    private Timestamp atualizationDate;

}
