package com.restaurante.ms_mesa.service;

import com.restaurante.ms_mesa.entity.MesaEntity;
import com.restaurante.ms_mesa.enumeration.StatusMesa;
import com.restaurante.ms_mesa.exceptions.CapacidadeInvalidaException;
import com.restaurante.ms_mesa.exceptions.MesaJaExistenteException;
import com.restaurante.ms_mesa.repository.MesaRepository;
import com.restaurante.ms_mesa.request.PostMesaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MesaServiceTest {

    @InjectMocks
    private MesaService mesaService;

    @Mock
    private MesaRepository mesaRepository;

    private PostMesaRequest postMesaRequest;

    private UUID id;

    private UUID idRepositorio;

    @Test
    void deveRetornarOIdAoCadastrarMesaComSucesso(){
        dadoUmaRequestValida();
        dadoQueORepositorioNaoRetorneUmaMesa();
        dadoQueORepositorioSalveUmaMesaComSucesso();
        quandoEuChamarOMetodoCriarMesa();
        entaoEsperoReceberOIdDaMesaCriada();
    }

    @Test
    void deveLancarErroQuandoUmNumeroDeMesaJaExistir(){
        dadoUmaRequestValida();
        dadoQueORepositorioRetorneUmaMesaComOMesmoNumero();
        entaoEsperoReceberUmErroDeNumeroExistente();
    }

    @Test
    void deveLancarErroQuandoCapacidadeForMenorOuIgualAZero(){
        dadoUmaRequestInvalida();
        entaoEsperoReceberUmErroDeCapacidadeInvalida();
    }

    void dadoUmaRequestValida(){
        postMesaRequest = new PostMesaRequest(1, StatusMesa.DISPONIVEL, 4);
    }

    void dadoUmaRequestInvalida(){
        postMesaRequest = new PostMesaRequest(3, StatusMesa.DISPONIVEL, 0);
    }

    void dadoQueORepositorioRetorneUmaMesaComOMesmoNumero(){

        MesaEntity mesaEntity = MesaEntity.builder()
                .id(UUID.randomUUID())
                .status(StatusMesa.DISPONIVEL)
                .capacidade(4)
                .numero(1)
                .dataDeAtualizacao(LocalDateTime.now())
                .build();

        Mockito.doReturn(Optional.of(mesaEntity))
                .when(mesaRepository)
                .findByNumero(ArgumentMatchers.anyInt());
    }

    void dadoQueORepositorioNaoRetorneUmaMesa(){
        Mockito.doReturn(Optional.empty()).when(mesaRepository).findByNumero(ArgumentMatchers.anyInt());
    }

    void dadoQueORepositorioSalveUmaMesaComSucesso(){

        idRepositorio = UUID.randomUUID();

        MesaEntity mesaEntity = MesaEntity.builder()
                .id(idRepositorio)
                .status(StatusMesa.DISPONIVEL)
                .capacidade(4)
                .numero(1)
                .dataDeAtualizacao(LocalDateTime.now())
                .build();

        Mockito.doReturn(mesaEntity).when(mesaRepository).save(ArgumentMatchers.any());

    }

    void quandoEuChamarOMetodoCriarMesa(){
        id = mesaService.criarMesa(postMesaRequest);
    }

    void entaoEsperoReceberOIdDaMesaCriada(){
        Assertions.assertEquals(idRepositorio.toString(), id.toString());

    }

    void entaoEsperoReceberUmErroDeNumeroExistente(){
        Assertions.assertThrows(MesaJaExistenteException.class, () -> quandoEuChamarOMetodoCriarMesa() );
    }

    void entaoEsperoReceberUmErroDeCapacidadeInvalida(){
        Assertions.assertThrows(CapacidadeInvalidaException.class, () -> quandoEuChamarOMetodoCriarMesa());
    }

}
