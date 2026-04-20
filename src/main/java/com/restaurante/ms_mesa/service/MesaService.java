package com.restaurante.ms_mesa.service;

import com.restaurante.ms_mesa.entity.MesaEntity;
import com.restaurante.ms_mesa.exceptions.CapacidadeInvalidaException;
import com.restaurante.ms_mesa.exceptions.MesaJaExistenteException;
import com.restaurante.ms_mesa.repository.MesaRepository;
import com.restaurante.ms_mesa.request.PostMesaRequest;
import com.restaurante.ms_mesa.response.MesaResponse;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<MesaResponse> buscarMesas(){

        List<MesaEntity> mesas = mesaRepository.findAll();

        return mesas.stream()
                .map(mesa -> MesaResponse.builder()
                        .id(mesa.getId())
                        .numero(mesa.getNumero())
                        .capacidade(mesa.getCapacidade())
                        .status(mesa.getStatus())
                        .build())
                .toList();
    }
}
     