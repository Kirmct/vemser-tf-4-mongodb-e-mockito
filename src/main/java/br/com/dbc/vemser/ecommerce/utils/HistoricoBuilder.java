package br.com.dbc.vemser.ecommerce.utils;

import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.service.UsuarioService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class HistoricoBuilder {

    private final UsuarioService usuarioService;


    public Historico inserirHistorico(String msg) throws RegraDeNegocioException {

        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.getLoggedUser();

        Historico historico = new Historico();

        if (usuarioLogadoDTO.getIdUsuario() != null) {
            Integer idUsuario = usuarioService.getIdLoggedUser();
            String cargo = usuarioService.findByRole(idUsuario);
            historico.setCargo(Cargo.valueOf(cargo));
            historico.setUsuario(usuarioLogadoDTO.getLogin() + ".");
        } else {
            historico.setCargo(Cargo.valueOf("REOLE_VISITANTE"));
            historico.setUsuario("Visitante");
        }
        historico.setAcao(msg);
        historico.setDataAcao(LocalDateTime.now());
        return historico;
    }
}
