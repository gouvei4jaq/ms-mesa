package com.restaurante.ms_mesa.service;

import com.restaurante.ms_mesa.entity.MesaEntity;
import com.restaurante.ms_mesa.exceptions.CapacidadeInvalidaException;
import com.restaurante.ms_mesa.exceptions.MesaJaExistenteException;
import com.restaurante.ms_mesa.repository.MesaRepository;
import com.restaurante.ms_mesa.request.PostMesaRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class MesaService {

    private MesaRepository mesaRepository;

    public UUID criarMesa(PostMesaRequest postMesaRequest) {

        if(postMesaRequest.getCapacidade() <= 0 ){
            throw new CapacidadeInvalidaException();
        }

        Optional<MesaEntity> validaMesa =  mesaRepository.findByNumero(postMesaRequest.getNumero());

        if (validaMesa.isPresent()){
           throw new MesaJaExistenteException();
        }

        MesaEntity mesaEntity = MesaEntity.builder()
                .status(postMesaRequest.getStatus())
                .numero(postMesaRequest.getNumero())
                .capacidade(postMesaRequest.getCapacidade())
                .dataDeAtualizacao(LocalDateTime.now())
                .build();

        MesaEntity mesaSalva = mesaRepository.save(mesaEntity);

        return mesaSalva.getId();
    }
}
