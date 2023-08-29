package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UserAtualizacaoDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import br.com.dbc.vemser.ecommerce.utils.BuscarUsuarioContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder bCrypt;
    private final CargoRepository cargoRepository;
    private final BuscarUsuarioContext buscarUsuarioContext;


    public Optional<UsuarioEntity> findByLogin(String login) {

        return usuarioRepository.findByLogin(login);
    }

    public Integer getIdLoggedUser() {
        return buscarUsuarioContext.idUsuarioLogado();
    }

    public UsuarioLogadoDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioLogadoDTO usuarioLogadoDTO = new UsuarioLogadoDTO();
        try {
            usuarioLogadoDTO = objectMapper.convertValue(findById(getIdLoggedUser()), UsuarioLogadoDTO.class);

        } catch (NumberFormatException e) {
            System.err.println("Teste");
        }
        return usuarioLogadoDTO;
    }

    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuário não encontrado!"));
    }

    public LoginDTO cadastro(LoginDTO user, Integer role) throws RegraDeNegocioException {
        if (role == null) {
            role = 2;
        }
        if (role < 1 || role > 3) {
            throw new RegraDeNegocioException("Cargo não existente");
        }

        String senhaCript = bCrypt.encode(user.getSenha());

        UsuarioEntity novoUser = new UsuarioEntity();
        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(role);
        novoUser.getCargos().add(cargo);
        novoUser.setSenha(senhaCript);
        novoUser.setLogin(user.getLogin());
        usuarioRepository.save(novoUser);
        return user;
    }

    public void atualizarSenha(LoginDTO loginDTO) throws RegraDeNegocioException {

        UsuarioEntity usuarioRecuperado = findByLogin(loginDTO.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não cadastrado!"));

        usuarioRecuperado.setSenha(bCrypt.encode(loginDTO.getSenha()));

        usuarioRepository.save(usuarioRecuperado);

    }

    public void desativarUsuario(String login) throws RegraDeNegocioException {

        UsuarioEntity usuarioRecuperado = findByLogin(login)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não cadastrado!"));

        usuarioRecuperado.setCargos(new HashSet<>());

        usuarioRepository.save(usuarioRecuperado);
    }

    public void atualizarUsuario(String login, UserAtualizacaoDTO userAtualizacaoDTO) throws RegraDeNegocioException {

        String loginAtualizado = userAtualizacaoDTO.getLogin();
        String cargo = userAtualizacaoDTO.getCargo().toString();

        UsuarioEntity usuarioRecuperado = findByLogin(login).orElseThrow(() -> new RegraDeNegocioException("Usuário não cadastrado!"));

        CargoEntity cargoRecuperado = cargoRepository.findByNome(cargo).orElseThrow(() -> new RegraDeNegocioException("Cargo não cadastrado!"));

        if (userAtualizacaoDTO.getLogin() != null) usuarioRecuperado.setLogin(loginAtualizado);
        if (userAtualizacaoDTO.getCargo() != null) usuarioRecuperado.getCargos().add(cargoRecuperado);

        usuarioRepository.save(usuarioRecuperado);

    }

}