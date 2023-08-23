package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UserAtualizacaoDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
//        return usuarioRepository.findByLoginAndSenha(login, senha);
//    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public Integer getIdLoggedUser() {
        String index = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return Integer.parseInt(index);
    }

    public UsuarioLogadoDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioLogadoDTO usuarioLogadoDTO = objectMapper.convertValue(findById(getIdLoggedUser()), UsuarioLogadoDTO.class);
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

        Optional<UsuarioEntity> usuarioRecuperado = findByLogin(loginDTO.getLogin());
        if (usuarioRecuperado.isEmpty()) throw new RegraDeNegocioException("Usuário não cadastrado!");

        usuarioRecuperado.get().setSenha(bCrypt.encode(loginDTO.getSenha()));

        usuarioRepository.save(usuarioRecuperado.get());

    }

    public void desativarUsuario(String login) throws RegraDeNegocioException {

        Optional<UsuarioEntity> usuarioRecuperado = findByLogin(login);
        if (usuarioRecuperado.isEmpty()) throw new RegraDeNegocioException("Usuário não cadastrado!");

        usuarioRecuperado.get().setCargos(new HashSet<>());

        usuarioRepository.save(usuarioRecuperado.get());
    }

    public void atualizarUsuario(String login, UserAtualizacaoDTO userAtualizacaoDTO) throws RegraDeNegocioException {

        String loginAtualizado = userAtualizacaoDTO.getLogin();
        String cargo = userAtualizacaoDTO.getCargo().toString();

        Optional<UsuarioEntity> usuarioRecuperado = findByLogin(login);
        if (usuarioRecuperado.isEmpty()) throw new RegraDeNegocioException("Usuário não cadastrado!");
        Optional<CargoEntity> cargoRecuperado = cargoRepository.findByNome(cargo);
        if (cargoRecuperado.isEmpty()) throw new RegraDeNegocioException("Cargo não cadastrado!");

        if (userAtualizacaoDTO.getLogin() != null) usuarioRecuperado.get().setLogin(loginAtualizado);
        if (userAtualizacaoDTO.getCargo() != null) usuarioRecuperado.get().getCargos().add(cargoRecuperado.get());

        usuarioRepository.save(usuarioRecuperado.get());

    }
}