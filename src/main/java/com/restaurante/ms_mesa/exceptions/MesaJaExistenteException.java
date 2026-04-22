package com.restaurante.ms_mesa.exceptions;

public class MesaJaExistenteException extends RuntimeException {
    public MesaJaExistenteException (Integer numero){
        super("Já existe uma mesa com o número: " + numero);
    }
}
