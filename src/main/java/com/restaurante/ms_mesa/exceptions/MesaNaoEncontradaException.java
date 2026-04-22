package com.restaurante.ms_mesa.exceptions;

import java.util.UUID;

public class MesaNaoEncontradaException extends RuntimeException {
    public MesaNaoEncontradaException (UUID id){
        super("Não foi encontrada uma mesa para esse id : " + id);
    }
    }

