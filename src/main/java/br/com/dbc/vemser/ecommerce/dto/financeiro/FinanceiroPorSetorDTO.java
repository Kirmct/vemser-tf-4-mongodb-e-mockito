package br.com.dbc.vemser.ecommerce.dto.financeiro;


import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceiroPorSetorDTO {


    @Id
    private TipoSetor setor;
    private Double totalValor;
}
