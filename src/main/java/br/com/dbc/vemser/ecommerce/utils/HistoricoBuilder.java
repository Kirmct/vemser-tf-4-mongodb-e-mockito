package br.com.dbc.vemser.ecommerce.utils;

import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import br.com.dbc.vemser.ecommerce.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class HistoricoBuilder {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoRepository historicoRepository;


    public Historico inserirHistorico(String msg, Setor setor) throws RegraDeNegocioException {

        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.getLoggedUser();

        Historico historico = new Historico();

        if (usuarioLogadoDTO.getIdUsuario() != null) {
            Integer idUsuario = usuarioService.getIdLoggedUser();
            String cargo = usuarioRepository.findByRole(idUsuario);
            historico.setCargo(Cargo.valueOf(cargo));
            historico.setUsuario(usuarioLogadoDTO.getLogin() + ".");
        } else {
            historico.setCargo(Cargo.valueOf("ROLE_VISITANTE"));
            historico.setUsuario("Visitante");
        }
        historico.setAcao(msg);
        historico.setSetor(setor);
        historico.setDataAcao(LocalDateTime.now());
        historicoRepository.save(historico);
        return historico;
    }
}
