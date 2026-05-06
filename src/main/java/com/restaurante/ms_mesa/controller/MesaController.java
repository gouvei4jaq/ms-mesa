package com.restaurante.ms_mesa.controller;

import com.restaurante.ms_mesa.enumeration.StatusMesa;
import com.restaurante.ms_mesa.request.PatchMesaRequest;
import com.restaurante.ms_mesa.request.PostMesaRequest;
import com.restaurante.ms_mesa.response.MesaResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    public void criaMesa(PostMesaRequest postMesaRequest){

    }

    public List<MesaResponse> listaMesas(StatusMesa statusMesa, Integer capacidade){
        return null;
    }

    public MesaResponse listaMesa(UUID id){
        return null;
    }

    public void atualizaMesa(PatchMesaRequest patchMesaRequest, UUID id){
    }

    public void deletaMesa(UUID id){

    }

}
