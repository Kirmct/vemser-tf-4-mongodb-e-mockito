package br.com.dbc.vemser.ecommerce.service;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
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

    private UsuarioEntity criarUsuario() {
        String CARGO_NOME = "ROLE_ADMIN";
        String LOGIN = "usuario@usuario.com";
        String SENHA = "123";

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome(CARGO_NOME);
        cargoEntity.setIdCargo(1);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setLogin(LOGIN);
        usuarioEntity.setLogin(SENHA);
        usuarioEntity.setCargos(new HashSet<>(List.of(cargoEntity)));

        return usuarioEntity;
    }
}