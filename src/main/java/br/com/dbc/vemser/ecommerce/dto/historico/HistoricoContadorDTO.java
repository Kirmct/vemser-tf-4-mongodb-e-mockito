package br.com.dbc.vemser.ecommerce.dto.historico;

import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import lombok.*;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoContadorDTO {

    @Id
    private Cargo cargo;
    private Integer quantidade;
}
