package com.restaurante.ms_mesa.response;

import com.restaurante.ms_mesa.enumeration.StatusMesa;

import java.util.UUID;

public class MesaResponse {
    private UUID id;
    private Integer numero;
    private StatusMesa status;
    private Integer capacidade;
}
