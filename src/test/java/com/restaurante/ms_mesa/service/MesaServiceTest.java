package com.restaurante.ms_mesa.service;

import com.restaurante.ms_mesa.entity.MesaEntity;
import com.restaurante.ms_mesa.enumeration.StatusMesa;
import com.restaurante.ms_mesa.exceptions.CapacidadeInvalidaException;
import com.restaurante.ms_mesa.exceptions.MesaJaExistenteException;
import com.restaurante.ms_mesa.exceptions.MesaNaoEncontradaException;
import com.restaurante.ms_mesa.repository.MesaRepository;
import com.restaurante.ms_mesa.request.PatchMesaRequest;
import com.restaurante.ms_mesa.request.PostMesaRequest;
import com.restaurante.ms_mesa.response.MesaResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MesaServiceTest {

    @InjectMocks
    private MesaService mesaService;

    @Mock
    private MesaRepository mesaRepository;

    private PostMesaRequest postMesaRequest;

    private PatchMesaRequest patchMesaRequest;

    private List<MesaResponse> resposta;

    private MesaResponse respostaId;

    private UUID id;

    private UUID idRepositorio;

    private List<MesaEntity> mesas;

    @Test
    void deveRetornarOIdAoCadastrarMesaComSucesso(){
        dadoUmaRequestValida();
        dadoQueORepositorioNaoRetorneUmaMesa();
        dadoQueORepositorioSalveUmaMesaComSucesso();
        quandoEuChamarOMetodoCriarMesa();
        entaoEsperoReceberOIdDaMesaCriada();
    }

    @Test
    void deveLancarErroQuandoCadastrarUmaMesaComUmNumeroJaExistente(){
        dadoUmaRequestValida();
        dadoQueORepositorioRetorneUmaMesaComOMesmoNumero();
        entaoEsperoReceberUmErroDeNumeroExistente();
    }

    @ParameterizedTest
    @ValueSource(ints = {0,-1,-5})
    void deveLancarErroQuandooQuandoCadastrarUmaMesaComCapacidadeMenorOuIgualAZero(int capacidadeInvalida){
        dadoUmaRequestInvalida(capacidadeInvalida);
        entaoEsperoReceberUmErroDeCapacidadeInvalida();
    }

    @Test
    void deveGarantirQueORepositorioSalvouAMesa(){
        dadoUmaRequestValida();
        dadoQueORepositorioNaoRetorneUmaMesa();
        dadoQueORepositorioSalveUmaMesaComSucesso();
        quandoEuChamarOMetodoCriarMesa();
        entaoORepositorioDeveTerSidoChamadoParaSalvar();
    }

    @Test
    void deveRetornarTodasAsMesasQuandoNaoHouverFiltro(){
        dadoQueExistemMesasNoRepositorio();
        quandoEuChamarOMetodoBuscarMesasSemPassarNenhumFiltro();
        entaoEsperoReceberUmaListaDeMesasSemFiltro();
    }

    @Test
    void deveRetornarMesasFiltradasPorStatus(){
        dadoQueExistemMesasComStatusDisponivel();
        quandoEuChamarOMetodoBuscarMesasComFiltroDeStatus();
        entaoEsperoReceberApenasMesasComOStatusEspecificado();
    }

    @Test
    void deveRetornarMesasFiltradasPorCapacidade(){
        dadoQueExistemMesasComCapacidadeTres();
        quandoEuChamarOMetodoBuscarMesasComFiltroDeCapacidade();
        entaoEsperoReceberApenasMesasComACapacidadeEspecificada();
    }

    @Test
    void deveRetornarMesasFiltradasPorCapacidadeEStatus(){
        dadoQueExistemMesasComStatusDisponivelECapacidadeDois();
        quandoEuChamarOMetodoBuscarMesasComFiltroDeCapacidadeEStatus();
        entaoEsperoReceberApenasMesasComACapacidadeEStatusEspecificados();
    }

    @Test
    void deveRetornarMesaAoBuscarPorIdExistente() {
        dadoQueExisteUmaMesaNoRepositorioComId();
        quandoEuChamarOMetodoBuscarPorId();
        entaoEsperoReceberAMesaComIdCorreto();
    }

    @Test
    void deveLancarErroAoBuscarMesaComIdInexistente() {
        dadoQueORepositorioNaoEncontreUmaMesaPeloId();
        entaoEsperoUmErroDeMesaNaoEncontradaException();
    }

    @Test
    void deveAtualizarMesaComSucesso(){
        dadoQueExisteUmaMesaNoRepositorioComId();
        dadoUmaRequestDeAtualizacaoValida();
        quandoEuChamarOMetodoAtualizarMesa();
        entaoORepositorioDeveTerSidoChamadoParaSalvarAtualizacao();
    }

    @Test
    void deveLancarErroAoTentarAtualizarMesaComIdInexistente(){
        dadoQueORepositorioNaoEncontreUmaMesaPeloId();
        dadoUmaRequestDeAtualizacaoValida();
        entaoEsperoUmErroDeMesaNaoEncontradaException();
    }

    private void dadoQueExistemMesasNoRepositorio() {

        mesas = List.of(
                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(2)
                        .capacidade(6)
                        .status(StatusMesa.OCUPADA)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build(),
                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(13)
                        .capacidade(2)
                        .status(StatusMesa.DISPONIVEL)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build()
        );

        Mockito.doReturn(mesas).when(mesaRepository).findByFilters(null,null);
    }

    private void dadoQueExistemMesasComStatusDisponivel() {

        mesas = List.of(
                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(1)
                        .capacidade(4)
                        .status(StatusMesa.DISPONIVEL)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build(),

                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(2)
                        .capacidade(2)
                        .status(StatusMesa.DISPONIVEL)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build()
        );

        Mockito.doReturn(mesas).when(mesaRepository).findByFilters(StatusMesa.DISPONIVEL, null);
    }

    private void dadoQueExistemMesasComStatusDisponivelECapacidadeDois() {

        mesas = List.of(
                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(10)
                        .capacidade(2)
                        .status(StatusMesa.DISPONIVEL)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build()
        );

        Mockito.doReturn(mesas).when(mesaRepository).findByFilters(StatusMesa.DISPONIVEL, 2);

    }
    private void dadoQueExistemMesasComCapacidadeTres() {

        mesas = List.of(
                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(3)
                        .capacidade(3)
                        .status(StatusMesa.DISPONIVEL)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build(),

                MesaEntity.builder()
                        .id(UUID.randomUUID())
                        .numero(4)
                        .capacidade(3)
                        .status(StatusMesa.OCUPADA)
                        .dataDeAtualizacao(LocalDateTime.now())
                        .build()
        );

        Mockito.doReturn(mesas).when(mesaRepository).findByFilters(null,3);

    }

    private void dadoQueExisteUmaMesaNoRepositorioComId() {

        id = UUID.randomUUID();

        MesaEntity mesa = MesaEntity.builder()
                .id(id)
                .numero(5)
                .capacidade(4)
                .status(StatusMesa.DISPONIVEL)
                .dataDeAtualizacao(LocalDateTime.now())
                .build();

        Mockito.doReturn(Optional.of(mesa)).when(mesaRepository).findById(id);

    }

    private void dadoUmaRequestDeAtualizacaoValida(){
        patchMesaRequest = new PatchMesaRequest(10, StatusMesa.OCUPADA, 5);
    }

    private void dadoUmaRequestValida(){
        postMesaRequest = new PostMesaRequest(1, StatusMesa.DISPONIVEL, 3);
    }

    private void dadoUmaRequestInvalida(int capacidadeInvalida){
        postMesaRequest = new PostMesaRequest(3, StatusMesa.DISPONIVEL, capacidadeInvalida);
    }

    private void dadoQueORepositorioRetorneUmaMesaComOMesmoNumero(){

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

    private void dadoQueORepositorioNaoRetorneUmaMesa(){
        Mockito.doReturn(Optional.empty()).when(mesaRepository).findByNumero(ArgumentMatchers.anyInt());
    }

    private void dadoQueORepositorioSalveUmaMesaComSucesso(){

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

    private void dadoQueORepositorioNaoEncontreUmaMesaPeloId(){
        Mockito.doReturn(Optional.empty()).when(mesaRepository).findById(ArgumentMatchers.any());

    }

    private void quandoEuChamarOMetodoCriarMesa(){
        id = mesaService.criarMesa(postMesaRequest);
    }

    private void quandoEuChamarOMetodoBuscarMesasSemPassarNenhumFiltro(){
        resposta = mesaService.buscarMesas(null, null);
    }

    private void quandoEuChamarOMetodoBuscarMesasComFiltroDeStatus(){
        resposta = mesaService.buscarMesas(StatusMesa.DISPONIVEL, null);
    }

    private void quandoEuChamarOMetodoBuscarMesasComFiltroDeCapacidade(){
        resposta = mesaService.buscarMesas(null, 3);
    }

    private void quandoEuChamarOMetodoBuscarMesasComFiltroDeCapacidadeEStatus(){
        resposta = mesaService.buscarMesas(StatusMesa.DISPONIVEL, 2);
    }

    private void quandoEuChamarOMetodoBuscarPorId(){
        respostaId = mesaService.buscarMesaPorId(id);
    }

    private void quandoEuChamarOMetodoAtualizarMesa(){
        mesaService.atualizarMesa(id, patchMesaRequest);
    }

    private void entaoEsperoReceberOIdDaMesaCriada(){
        Assertions.assertEquals(idRepositorio.toString(), id.toString());

    }

    private void entaoEsperoReceberUmErroDeNumeroExistente(){
        Assertions.assertThrows(MesaJaExistenteException.class, () -> quandoEuChamarOMetodoCriarMesa() );
    }

    private void entaoEsperoReceberUmErroDeCapacidadeInvalida(){
        Assertions.assertThrows(CapacidadeInvalidaException.class, () -> quandoEuChamarOMetodoCriarMesa());
    }

    private void entaoORepositorioDeveTerSidoChamadoParaSalvar() {
        Mockito.verify(mesaRepository, Mockito.times(1)).save(ArgumentMatchers.any(MesaEntity.class));
    }

    private void entaoEsperoReceberApenasMesasComOStatusEspecificado(){
        Assertions.assertTrue(resposta.stream().allMatch(mesa -> mesa.getStatus() == StatusMesa.DISPONIVEL));
    }

    private void entaoEsperoReceberApenasMesasComACapacidadeEspecificada(){
        Assertions.assertTrue(resposta.stream().allMatch(mesa -> mesa.getCapacidade() == 3));
    }

    private void entaoEsperoReceberUmaListaDeMesasSemFiltro(){
        Assertions.assertEquals(2, resposta.size());
    }

    private void entaoEsperoReceberApenasMesasComACapacidadeEStatusEspecificados(){
        Assertions.assertTrue(resposta.stream().anyMatch(mesa -> mesa.getCapacidade() == 2 && mesa.getStatus() == StatusMesa.DISPONIVEL));
    }

    private void entaoEsperoReceberAMesaComIdCorreto(){
        Assertions.assertEquals(id, respostaId.getId());
    }

    private void entaoEsperoUmErroDeMesaNaoEncontradaException(){
        Assertions.assertThrows(MesaNaoEncontradaException.class,
                () -> mesaService.buscarMesaPorId(UUID.randomUUID()));
    }

    private void entaoORepositorioDeveTerSidoChamadoParaSalvarAtualizacao(){
        Mockito.verify(mesaRepository, Mockito.times(1)).save(ArgumentMatchers.any(MesaEntity.class));
    }
}
