package com.restaurante.ms_mesa.request;

import com.restaurante.ms_mesa.enumeration.StatusMesa;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostMesaRequest {
    private Integer numero;
    private StatusMesa status;
    private Integer capacidade;
}
