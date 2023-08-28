package br.com.dbc.vemser.ecommerce.dto.financeiro;


import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class FinanceiroPorSetorDTO {


    @Id
    private TipoSetor setor;
    private Double totalValor;
}
