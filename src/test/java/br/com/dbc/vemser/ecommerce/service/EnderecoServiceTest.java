package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.entity.*;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.EnderecoRepository;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;
    @Mock
    private ConverterEnderecoParaDTOutil converterEnderecoParaDTOutil;
    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private HistoricoBuilder historicoBuilder;

    @Mock
    private HistoricoRepository historicoRepository;

    private ConverterEnderecoParaDTOutil conversorDTO =
            new ConverterEnderecoParaDTOutil(new ObjectMapper());
    private List<EnderecoEntity> listaEnderecos = new ArrayList<>();
    private List<EnderecoDTO> listaEnderecoDTO = new ArrayList<>();

    private CargoEntity cargoEntity = new CargoEntity();
    private  UsuarioEntity usuarioEntity = new UsuarioEntity();


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
        usuarioEntity.setCargos(new HashSet<>(Arrays.asList(cargoEntity)));



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
                .map(conversorDTO::converterByEnderecoDTO).toList();

    }

    @Test
    void listarEnderecos() throws Exception {


        AtomicInteger contador = new AtomicInteger(0);
        AtomicInteger contador2 = new AtomicInteger(0);

        Mockito.when(enderecoRepository.findAll()).thenReturn(listaEnderecos);

        Mockito.when(converterEnderecoParaDTOutil
                        .converterByEnderecoDTO(any(EnderecoEntity.class)))
                .thenReturn(listaEnderecoDTO.get(contador2.getAndIncrement()));

        String MENSAGEM_ACAO = "Realizando listagem de endereços";


        Historico historico = criarHistorico(MENSAGEM_ACAO);

        Mockito.when(historicoBuilder.inserirHistorico(any()))
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
    void buscaEnderecoById() throws RegraDeNegocioException{

        Integer idEndereco = 1;

        Mockito.when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.ofNullable(listaEnderecos.get(0))) ;

        Mockito.when(converterEnderecoParaDTOutil
                        .converterByEnderecoDTO(any(EnderecoEntity.class)))
                .thenReturn(listaEnderecoDTO.get(0));

        EnderecoDTO enderecoById = enderecoService.getEnderecoById(idEndereco);

        Assertions.assertNotNull(enderecoById);
        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> enderecoService.getEnderecoById(2));
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
}