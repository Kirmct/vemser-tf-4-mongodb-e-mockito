package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.AuthControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UserAtualizacaoDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.security.TokenService;
import br.com.dbc.vemser.ecommerce.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {
    public final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        UsuarioEntity usuarioValidado = (UsuarioEntity) authentication.getPrincipal();

        return tokenService.generateToken(usuarioValidado);
    }

    @PostMapping("/cadastro")
    public LoginDTO cadastro(@RequestBody LoginDTO user, @RequestParam(value = "role", required = false) Integer role) throws RegraDeNegocioException {
        return usuarioService.cadastro(user, role);
    }

    @GetMapping("/usuario-logado")
    public UsuarioLogadoDTO recuperarUsuarioLogado() throws RegraDeNegocioException {


        return usuarioService.getLoggedUser();
    }

    @PutMapping("/usuario-trocar-senha")
    public ResponseEntity<Void> alterarSenhaDeUsuario(@RequestBody LoginDTO loginDTO) throws RegraDeNegocioException {

        usuarioService.atualizarSenha(loginDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/desativar-usuario/{login}")
    public ResponseEntity<Void> desativarUsuarioLogado(@NotBlank(message = "Informe o login.")
                                                       @PathVariable String login)
                                                       throws RegraDeNegocioException {

        usuarioService.desativarUsuario(login);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/atualizar-usuario/{login}")
    public ResponseEntity<Void> atualizarUsuario(@NotBlank(message = "Informe o login.")
                                                 @PathVariable String login,
                                                 @RequestBody UserAtualizacaoDTO userAtualizacaoDTO)
            throws RegraDeNegocioException {

        usuarioService.atualizarUsuario(login, userAtualizacaoDTO);

        return ResponseEntity.ok().build();
    }

}