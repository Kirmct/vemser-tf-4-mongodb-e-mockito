package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder bCrypt;
    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }


    @Test
    void findByLogin() {

        String LOGIN = "usuario@usuario.com";

        when(usuarioRepository.findByLogin(any())).thenReturn(Optional.of(criarUsuario()));

        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByLogin(LOGIN);

        Assertions.assertNotNull(usuarioEntity.get());
    }

    @Test
    void quandoNaoEncontrarUmUsuarioOptionalVazio() {

        String LOGIN = "usuario@usuario.com";

        lenient().when(usuarioRepository.findByLogin(LOGIN))
                .thenReturn(Optional.of(criarUsuario()));

        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByLogin("");

        Assertions.assertTrue(usuarioEntity.isEmpty());

    }

    @Test
    void buscarPorId() throws RegraDeNegocioException {

        Integer idUsuario = 1;

        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(criarUsuario()));

        UsuarioEntity usuarioEntity =
                usuarioService.findById(idUsuario);

        Assertions.assertNotNull(usuarioEntity);
    }


    @Test
    void quandoNaoEncontrarUmUsuarioPorIdLancarException() {

        Integer idUsuario = 1;

        lenient().when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(criarUsuario()));

        Assertions.assertThrows(RegraDeNegocioException.class,
                () -> usuarioService.findById(2));

    }

    @Test
    void cadastroUsuario() throws RegraDeNegocioException {

        Integer role = 1;
        String login = "usuario@usuario.com";
        String senha = "123";
        String bcrypt = "$2a$12$TPmEEhmwn2zwFcraq0PHkeSj5uk9ze6a4pghsTTi59yheZXzWRBMO";


       lenient().when(usuarioService.cadastro(criarLoginDTO(login, senha), role))
                .thenReturn(criarLoginDTO(login, senha));

        when(bCrypt.encode(any())).thenReturn(bcrypt);

        LoginDTO cadastro1 = usuarioService.cadastro(criarLoginDTO(login, senha), 1);

         verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));

        Assertions.assertNotNull(cadastro1);
    }


    @Test
    void erroCadastroUsuarioNaoEncontrado() throws RegraDeNegocioException {

        Integer role = 1;
        String login = "usuario@usuario.com";
        String senha = "123";


        lenient().when(usuarioService.cadastro(criarLoginDTO(login, senha), role))
                .thenReturn(criarLoginDTO(login, senha));

        Assertions.assertThrows(RegraDeNegocioException.class, () ->
                usuarioService.cadastro(criarLoginDTO("qualquer", senha), 6) );
    }

    private UsuarioEntity criarUsuario() {
        String CARGO_NOME = "ROLE_ADMIN";
        String LOGIN = "usuario@usuario.com";
        String SENHA = "123";

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome(CARGO_NOME);


        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setLogin(LOGIN);
        usuarioEntity.setSenha(SENHA);
        usuarioEntity.setCargos(new HashSet<>(List.of(cargoEntity)));

        return usuarioEntity;
    }

    private UsuarioEntity criarUsuarioComRole(LoginDTO loginDTO, Integer role) {

        String LOGIN = loginDTO.getLogin();
        String SENHA = loginDTO.getSenha();

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(role);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setLogin(LOGIN);
        usuarioEntity.setSenha(SENHA);

        return usuarioEntity;
    }

    private LoginDTO criarLoginDTO(String login, String senha) {

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setLogin(login);
        loginDTO.setSenha(senha);

        return loginDTO;
    }
}