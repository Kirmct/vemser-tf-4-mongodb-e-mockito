package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.entity.*;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.EnderecoRepository;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;
    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private HistoricoBuilder historicoBuilder;

    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    private List<EnderecoEntity> listaEnderecos = new ArrayList<>();
    private List<EnderecoDTO> listaEnderecoDTO = new ArrayList<>();

    private CargoEntity cargoEntity = new CargoEntity();
    private UsuarioEntity usuarioEntity = new UsuarioEntity();


    @BeforeEach
    void configurarUsusario() {

        String CARGO_NOME = "ROLE_ADMIN";
        String LOGIN = "admin";
        String SENHA = "123";

        cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome(CARGO_NOME);
        cargoEntity.setIdCargo(1);

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setLogin(LOGIN);
        usuarioEntity.setSenha(SENHA);
    }

    @BeforeEach
    void carregarListaEnderecos() {

        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setIdCliente(1);
        cliente1.setNome("cliente 1");
        cliente1.setCpf("3453451");
        cliente1.setEmail("cliente@cliente.com");

        EnderecoEntity endereco =
                new EnderecoEntity(1, cliente1, "Rua a",
                        123, "casa", "34553453",
                        "Bairro a", "cidade a", "estado a");

        EnderecoEntity endereco2 =
                new EnderecoEntity(2, cliente1, "Rua b",
                        1234, "apt", "353463",
                        "Bairro b", "cidade b", "estado b");

        listaEnderecos.add(endereco);
        listaEnderecos.add(endereco2);

        listaEnderecoDTO = listaEnderecos.stream()
                .map(ConverterEnderecoParaDTOutil::converterByEnderecoDTO).toList();

    }

    @Test
    void listarEnderecos() throws Exception {

        Mockito.when(enderecoRepository.findAll()).thenReturn(listaEnderecos);
        
        String MENSAGEM_ACAO = "Realizando listagem de endereços";


        Historico historico = criarHistorico(MENSAGEM_ACAO);

        Mockito.when(historicoBuilder.inserirHistorico(any(), any()))
                .thenReturn(historico);

        Mockito.when(historicoRepository.save(any(Historico.class)))
                .thenReturn(historico);


        List<EnderecoDTO> enderecoDTOS = enderecoService.listarEnderecos();

        Historico historicoPersistencia = historicoRepository.save(historico);

        Assertions.assertNotNull(enderecoDTOS);
        Assertions.assertTrue(enderecoDTOS.size() > 1);
        Assertions.assertNotNull(historicoPersistencia);
    }

    @Test
    void buscaEnderecoById() throws RegraDeNegocioException {

        Integer idEndereco = 1;

        Mockito.when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.ofNullable(listaEnderecos.get(0)));

        EnderecoDTO enderecoById = enderecoService.getEnderecoById(idEndereco);

        Assertions.assertNotNull(enderecoById);
        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.getEnderecoById(2));
    }


    @Test
    void buscaEnderecoByIdCliente() throws RegraDeNegocioException {

        Integer idCliente = 1;


        Mockito.when(enderecoRepository.findEnderecoEntityByCliente_IdCliente(idCliente))
                .thenReturn(listaEnderecos);

        String MENSAGEM_ACAO = "Realizando listagem de endereços por cliente";


        Historico historico = criarHistorico(MENSAGEM_ACAO);

        Mockito.when(historicoBuilder.inserirHistorico(any(), any()))
                .thenReturn(historico);

        Mockito.when(historicoRepository.save(any(Historico.class)))
                .thenReturn(historico);

        Historico historicoPersistencia = historicoRepository.save(historico);


        List<EnderecoDTO> enderecoDTOS = enderecoService.listarEnderecoByIdCliente(idCliente);

        Assertions.assertEquals(listaEnderecoDTO, enderecoDTOS);
        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.listarEnderecoByIdCliente(2));
        Assertions.assertNotNull(historicoPersistencia);
    }

    @Test
    void criarEndereco() throws RegraDeNegocioException {

        Integer idCliente = 2;
        ClienteEntity clienteEntity = criarCliente(idCliente);
        EnderecoEntity endereco = listaEnderecos.get(0);
        endereco.setCliente(clienteEntity);
        EnderecoDTO enderecoDTO = ConverterEnderecoParaDTOutil.converterByEnderecoDTO(endereco);

        Mockito.when(clienteRepository.findById(idCliente))
                .thenReturn(Optional.of(clienteEntity));

        Mockito.when(enderecoRepository.save(any(EnderecoEntity.class)))
                .thenReturn(endereco);


        Historico historico = criarHistorico("Endereço criado!");

        Mockito.when(historicoBuilder.inserirHistorico(any(), any()))
                .thenReturn(historico);

        Mockito.when(historicoRepository.save(any(Historico.class)))
                .thenReturn(historico);

        Historico historicoPersistencia = historicoRepository.save(historico);


        EnderecoDTO enderecoCriado = enderecoService.create(idCliente, enderecoCreateDTO());


        Assertions.assertNotNull(historicoPersistencia);

        Assertions.assertEquals(enderecoCriado, enderecoDTO);

        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.create(1, enderecoCreateDTO()));
    }

    @Test
    void atualizarEndereco() throws RegraDeNegocioException {

        Integer idEndereco = 1;

        EnderecoEntity endereco = listaEnderecos.get(0);


        Mockito.when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.ofNullable(endereco));


        endereco.setLogradouro("Rua Nova");

        Mockito.when(enderecoRepository.save(any(EnderecoEntity.class))).thenReturn(endereco);

        String MENSAGEM_ENDERECO = "Endereço atualizado com sucesso!";

        Historico historico = criarHistorico(MENSAGEM_ENDERECO);

        Mockito.when(historicoBuilder.inserirHistorico(any(), any()))
                .thenReturn(historico);

        Mockito.when(historicoRepository.save(any(Historico.class)))
                .thenReturn(historico);

        Historico historicoPersistencia = historicoRepository.save(historico);


        EnderecoDTO enderecoAtualizado = enderecoService.update(idEndereco, enderecoCreateDTO());


        Assertions.assertEquals(historicoPersistencia.getAcao(), "Endereço atualizado com sucesso!");

        Assertions.assertNotNull(enderecoAtualizado);
        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.update(2, enderecoCreateDTO()));
    }



    @Test
    void deletarEndereco() throws RegraDeNegocioException {

        Integer idEndereco = 1;

        EnderecoEntity endereco = listaEnderecos.get(0);

        Mockito.when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.ofNullable(endereco));

        Mockito.doNothing().when(enderecoRepository).delete(endereco);


        String MENSAGEM_ENDERECO = "Endereço deletado com sucesso!";

        Historico historico = criarHistorico(MENSAGEM_ENDERECO);

        Mockito.when(historicoBuilder.inserirHistorico(any(), any()))
                .thenReturn(historico);

        Mockito.when(historicoRepository.save(any(Historico.class)))
                .thenReturn(historico);

        Historico historicoPersistencia = historicoRepository.save(historico);

        enderecoService.delete(idEndereco);



        Assertions.assertNotNull(historicoPersistencia);

        Mockito.verify(enderecoRepository,
                Mockito.times(1)).delete(endereco);

        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.delete(2));

    }


    private Historico criarHistorico(String MENSAGEM_ACAO) {
        Historico historico = new Historico();
        historico.setId("1");
        historico.setUsuario(usuarioEntity.getUsername());
        historico.setDataAcao(LocalDateTime.now());
        historico.setAcao(MENSAGEM_ACAO);
        historico.setCargo(Cargo.valueOf(cargoEntity.getNome()));
        return historico;
    }

    private EnderecoCreateDTO enderecoCreateDTO() {

        return new EnderecoCreateDTO("Rua a",
                123, "casa", "34553453",
                "Bairro a", "cidade a", "estado a");
    }


    private ClienteEntity criarCliente(Integer idCliente) {

        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdCliente(idCliente);
        cliente.setNome("cliente " + idCliente);
        cliente.setCpf("3453451");
        cliente.setEmail("cliente@cliente.com");

        return cliente;
    }
}