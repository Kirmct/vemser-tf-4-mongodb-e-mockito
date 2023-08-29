//package br.com.dbc.vemser.ecommerce.service;
//
//import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoDTO;
//import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
//import br.com.dbc.vemser.ecommerce.entity.Historico;
//import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
//import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
//import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
//import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
//import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class HistoricoServiceTest {
//
//    @InjectMocks
//    private HistoricoService historicoService;
//
//    @Mock
//    private HistoricoRepository historicoRepository;
//
//    @Mock
//    private HistoricoBuilder historicoBuilder;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    private List<HistoricoDTO> listaHistoricoDTO = new ArrayList<>();
//    private List<Historico> listaHistorico = new ArrayList<>();
//
//    private CargoEntity cargoEntity = new CargoEntity();
//    private UsuarioEntity usuarioEntity = new UsuarioEntity();
//
//
//
//
//
//    @BeforeEach
//    void inicializarUsuarioEhistoricos() {
//
//        String CARGO_NOME = "ROLE_ADMIN";
//        String LOGIN = "admin";
//        String SENHA = "123";
//
//        cargoEntity = new CargoEntity();
//        cargoEntity.setIdCargo(1);
//        cargoEntity.setNome(CARGO_NOME);
//        cargoEntity.setIdCargo(1);
//
//        usuarioEntity = new UsuarioEntity();
//        usuarioEntity.setIdUsuario(1);
//        usuarioEntity.setLogin(LOGIN);
//        usuarioEntity.setSenha(SENHA);
//
//        Historico historico = new Historico();
//        historico.setId("1");
//        historico.setCargo(Cargo.valueOf(cargoEntity.getNome()));
//        historico.setSetor(Setor.USUARIO);
//        historico.setUsuario(usuarioEntity.getUsername());
//        historico.setAcao("Listou todos os usuários");
//        historico.setDataAcao(LocalDateTime.now());
//
//        Historico historico2 = new Historico();
//        historico2.setId("2");
//        historico2.setCargo(Cargo.valueOf(cargoEntity.getNome()));
//        historico2.setSetor(Setor.ENDERECO);
//        historico2.setUsuario(usuarioEntity.getUsername());
//        historico2.setAcao("Listou todos os endereços");
//        historico2.setDataAcao(LocalDateTime.now());
//
//        listaHistorico.add(historico);
//        listaHistorico.add(historico2);
//
//        HistoricoDTO historicoDTO = new HistoricoDTO();
//        historicoDTO.setId("1");
//        historicoDTO.setCargo(Cargo.valueOf(cargoEntity.getNome()));
//        historicoDTO.setSetor(Setor.USUARIO);
//        historicoDTO.setUsuario(usuarioEntity.getUsername());
//        historicoDTO.setAcao("Listou todos os usuários");
//        historicoDTO.setDataAcao(LocalDateTime.now());
//
//        HistoricoDTO historicoDTO2 = new HistoricoDTO();
//        historicoDTO2.setId("2");
//        historicoDTO2.setCargo(Cargo.valueOf(cargoEntity.getNome()));
//        historicoDTO2.setSetor(Setor.ENDERECO);
//        historicoDTO2.setUsuario(usuarioEntity.getUsername());
//        historicoDTO2.setAcao("Listou todos os endereços");
//        historicoDTO2.setDataAcao(LocalDateTime.now());
//
//        listaHistoricoDTO.add(historicoDTO);
//        listaHistoricoDTO.add(historicoDTO2);
//
//    }
//
//    @Test
//    void findAll() {
//
//        lenient().when(objectMapper.convertValue(any(Historico.class), eq(HistoricoDTO.class)))
//                .thenReturn(listaHistoricoDTO.get(0))
//                .thenReturn(listaHistoricoDTO.get(1));
//
//        when(historicoRepository.findAll()).thenReturn(listaHistorico);
//
//
//        List<HistoricoDTO> listarHistoricosMetodo = historicoService.findAll();
//
//        Assertions.assertNotNull(listarHistoricosMetodo);
//        Assertions.assertEquals(listaHistoricoDTO,listarHistoricosMetodo);
//    }
//
//    @Test
//    void findById() throws RegraDeNegocioException {
//
//        String idHistorico = "1";
//
//
//        when(historicoRepository.findById(idHistorico))
//                .thenReturn(Optional.ofNullable(listaHistorico.get(0)));
//
//        lenient().when(objectMapper.convertValue(any(Historico.class), eq(HistoricoDTO.class)))
//                .thenReturn(listaHistoricoDTO.get(0));
//
//
//        HistoricoDTO historicoDTO = historicoService.findById(idHistorico);
//
//        Assertions.assertNotNull(historicoDTO);
//        Assertions.assertEquals(listaHistoricoDTO.get(0),historicoDTO);
//        Assertions.assertThrows(RegraDeNegocioException.class, () -> historicoService.findById("2"));
//    }
//
//    @Test
//    void findBycargo() throws RegraDeNegocioException {
//
//        Cargo cargo = Cargo.valueOf(cargoEntity.getNome());
//
//
//        when(historicoRepository.findAllByCargo(cargo))
//                .thenReturn(listaHistorico);
//
//        lenient().when(objectMapper.convertValue(any(Historico.class), eq(HistoricoDTO.class)))
//                .thenReturn(listaHistoricoDTO.get(0))
//                .thenReturn(listaHistoricoDTO.get(1));
//
//
//        List<HistoricoDTO> historicoDTO = historicoService.findByCargo(cargo);
//
//        Assertions.assertNotNull(historicoDTO);
//        Assertions.assertEquals(listaHistoricoDTO,historicoDTO);
//    }
//
//    @Test
//    void findBysetor()  {
//
//        Setor setor = Setor.USUARIO;
//
//
//        when(historicoRepository.findAllBySetor(setor))
//                .thenReturn(listaHistorico);
//
//        lenient().when(objectMapper.convertValue(any(Historico.class), eq(HistoricoDTO.class)))
//                .thenReturn(listaHistoricoDTO.get(0))
//                .thenReturn(listaHistoricoDTO.get(1));
//
//
//        List<HistoricoDTO> historicoDTO = historicoService.findBySetor(setor);
//
//        Assertions.assertNotNull(historicoDTO);
//        Assertions.assertEquals(listaHistoricoDTO,historicoDTO);
//    }
//}