package com.restaurante.ms_mesa.request;

import com.restaurante.ms_mesa.enumeration.StatusMesa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchMesaRequest {

    private Integer numero;
    private StatusMesa status;
    private Integer capacidade;
}
