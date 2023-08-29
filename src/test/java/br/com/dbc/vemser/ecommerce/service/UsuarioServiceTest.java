//package br.com.dbc.vemser.ecommerce.service;
//
//import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
//import br.com.dbc.vemser.ecommerce.dto.usuario.UserAtualizacaoDTO;
//import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
//import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
//import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
//import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
//import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
//import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
//import br.com.dbc.vemser.ecommerce.utils.BuscarUsuarioContext;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UsuarioServiceTest {
//    @InjectMocks
//    private UsuarioService usuarioService;
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private CargoRepository cargoRepository;
//
//    @Mock
//    private PasswordEncoder bCrypt;
//
//    @Mock
//    BuscarUsuarioContext buscarUsuarioContext;
//
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @BeforeEach
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
//    }
//
//
//    @Test
//    void findByLogin() {
//
//        String LOGIN = "usuario@usuario.com";
//
//        when(usuarioRepository.findByLogin(any())).thenReturn(Optional.of(criarUsuario()));
//
//        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByLogin(LOGIN);
//
//        Assertions.assertNotNull(usuarioEntity.get());
//    }
//
//    @Test
//    void quandoNaoEncontrarUmUsuarioOptionalVazio() {
//
//        String LOGIN = "usuario@usuario.com";
//
//        lenient().when(usuarioRepository.findByLogin(LOGIN))
//                .thenReturn(Optional.of(criarUsuario()));
//
//        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByLogin("");
//
//        Assertions.assertTrue(usuarioEntity.isEmpty());
//
//    }
//
//    @Test
//    void buscarPorId() throws RegraDeNegocioException {
//
//        Integer idUsuario = 1;
//
//        when(usuarioRepository.findById(idUsuario))
//                .thenReturn(Optional.of(criarUsuario()));
//
//        UsuarioEntity usuarioEntity =
//                usuarioService.findById(idUsuario);
//
//        Assertions.assertNotNull(usuarioEntity);
//    }
//
//
//    @Test
//    void quandoNaoEncontrarUmUsuarioPorIdLancarException() {
//
//        Integer idUsuario = 1;
//
//        lenient().when(usuarioRepository.findById(idUsuario))
//                .thenReturn(Optional.of(criarUsuario()));
//
//        Assertions.assertThrows(RegraDeNegocioException.class,
//                () -> usuarioService.findById(2));
//
//    }
//
//    @Test
//    void cadastroUsuario() throws RegraDeNegocioException {
//
//        Integer role = 1;
//        String login = "usuario@usuario.com";
//        String senha = "123";
//        String bcrypt = "$2a$12$TPmEEhmwn2zwFcraq0PHkeSj5uk9ze6a4pghsTTi59yheZXzWRBMO";
//
//
//        lenient().when(usuarioService.cadastro(criarLoginDTO(login, senha), role))
//                .thenReturn(criarLoginDTO(login, senha));
//
//        when(bCrypt.encode(any())).thenReturn(bcrypt);
//
//        LoginDTO cadastro1 = usuarioService.cadastro(criarLoginDTO(login, senha), 1);
//
//        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
//
//        Assertions.assertNotNull(cadastro1);
//    }
//
//
//    @Test
//    void erroCadastroUsuarioNaoEncontrado() throws RegraDeNegocioException {
//
//        Integer role = 1;
//        String login = "usuario@usuario.com";
//        String senha = "123";
//
//
//        lenient().when(usuarioService.cadastro(criarLoginDTO(login, senha), role))
//                .thenReturn(criarLoginDTO(login, senha));
//
//        Assertions.assertThrows(RegraDeNegocioException.class, () ->
//                usuarioService.cadastro(criarLoginDTO("qualquer", senha), 6));
//    }
//
//
//    @Test
//    void atualizarSenha() throws RegraDeNegocioException {
//
//
//        String login = "usuario@usuario.com";
//        String senha = "123";
//        String bcrypt = "$2a$12$TPmEEhmwn2zwFcraq0PHkeSj5uk9ze6a4pghsTTi59yheZXzWRBMO";
//
//
//        lenient().when(usuarioRepository.findByLogin(login))
//                .thenReturn(Optional.of(criarUsuario()));
//
//        when(bCrypt.encode(any())).thenReturn(bcrypt);
//
//        usuarioService.atualizarSenha(criarLoginDTO(login, senha));
//
//        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
//
//        Assertions.assertThrows(RegraDeNegocioException.class,
//                () -> usuarioService.atualizarSenha(criarLoginDTO("qualquer", "123456")));
//
//    }
//
//
//    @Test
//    void desativarUsuario() throws RegraDeNegocioException {
//
//        String login = "usuario@usuario.com";
//
//        UsuarioEntity usuarioEntity = criarUsuario();
//
//        lenient().when(usuarioRepository.findByLogin(login))
//                .thenReturn(Optional.of(usuarioEntity));
//
//
//        usuarioService.desativarUsuario(login);
//
//        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
//
//        Assertions.assertEquals(0, usuarioEntity.getCargos().size());
//
//        Assertions.assertThrows(RegraDeNegocioException.class,
//                () -> usuarioService.atualizarSenha(criarLoginDTO("qualquer", "123456")));
//
//    }
//
//
//    @Test
//    void buscarUsuarioLogado() throws RegraDeNegocioException {
//
//        UsuarioLogadoDTO usuarioLogadoDTO = criarUsuarioLogado();
//
//
//        when(buscarUsuarioContext.idUsuarioLogado()).thenReturn(1);
//
//
//        when(usuarioRepository.findById(usuarioLogadoDTO.getIdUsuario()))
//                .thenReturn(Optional.of(criarUsuario()));
//
//
//        UsuarioLogadoDTO loggedUser = usuarioService.getLoggedUser();
//
//        Assertions.assertNotNull(loggedUser);
//
//
//    }
//
//    @Test
//    public void atualizarUsuario() throws RegraDeNegocioException {
//
//        String loginAntigo = "usuario";
//
//        String loginNovo = "novoUser";
//
//        Cargo cargo = Cargo.ROLE_USUARIO;
//
//        CargoEntity cargoEntity = new CargoEntity();
//        cargoEntity.setIdCargo(2);
//        cargoEntity.setNome(cargo.toString());
//
//        when(usuarioRepository.findByLogin(loginAntigo))
//                .thenReturn(Optional.of(criarUsuario()));
//
//        when(cargoRepository.findByNome(cargo.toString()))
//                .thenReturn(Optional.of(cargoEntity));
//
//
//        usuarioService.atualizarUsuario(loginAntigo, criarUsuarioAtualizacao(loginNovo, cargo));
//
//        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
//
//    }
//
//    private UsuarioLogadoDTO criarUsuarioLogado() {
//        return new UsuarioLogadoDTO(1, "123", true);
//    }
//
//    private UserAtualizacaoDTO criarUsuarioAtualizacao(String login, Cargo cargo) {
//        return new UserAtualizacaoDTO(login, cargo);
//    }
//
//
//    private UsuarioEntity criarUsuario() {
//        String CARGO_NOME = "ROLE_ADMIN";
//        String LOGIN = "usuario@usuario.com";
//        String SENHA = "123";
//
//        CargoEntity cargoEntity = new CargoEntity();
//        cargoEntity.setIdCargo(1);
//        cargoEntity.setNome(CARGO_NOME);
//
//
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//        usuarioEntity.setIdUsuario(1);
//        usuarioEntity.setLogin(LOGIN);
//        usuarioEntity.setSenha(SENHA);
//        usuarioEntity.setCargos(new HashSet<>(List.of(cargoEntity)));
//
//        return usuarioEntity;
//    }
//
//    private UsuarioEntity criarUsuarioComRole(LoginDTO loginDTO, Integer role) {
//
//        String LOGIN = loginDTO.getLogin();
//        String SENHA = loginDTO.getSenha();
//
//        CargoEntity cargoEntity = new CargoEntity();
//        cargoEntity.setIdCargo(role);
//
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//        usuarioEntity.setIdUsuario(1);
//        usuarioEntity.setLogin(LOGIN);
//        usuarioEntity.setSenha(SENHA);
//
//        return usuarioEntity;
//    }
//
//    private LoginDTO criarLoginDTO(String login, String senha) {
//
//        LoginDTO loginDTO = new LoginDTO();
//
//        loginDTO.setLogin(login);
//        loginDTO.setSenha(senha);
//
//        return loginDTO;
//    }
//}