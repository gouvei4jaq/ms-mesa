package com.restaurante.ms_mesa.response;

import com.restaurante.ms_mesa.enumeration.StatusMesa;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class MesaResponse {
    private UUID id;
    private Integer numero;
    private StatusMesa status;
    private Integer capacidade;
}
