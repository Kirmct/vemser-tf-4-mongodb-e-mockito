package br.com.dbc.vemser.ecommerce.dto.historico;

import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoricoDTO {

    @Schema(description = "ID do histórico")
    private String id;

    private Cargo cargo;

    @Schema(description = "Usuário que fez a ação")
    private String usuario;

    @Schema(description = "Ação realizada")
    private String acao;

    @Schema(description = "Data e horário da ação")
    private LocalDateTime dataAcao;
}
