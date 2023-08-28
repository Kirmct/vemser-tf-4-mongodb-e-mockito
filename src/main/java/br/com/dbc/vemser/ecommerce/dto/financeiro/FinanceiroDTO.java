package br.com.dbc.vemser.ecommerce.dto.financeiro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceiroDTO {


    private String id;

    private Double total;

    private LocalDate dataDePagamento;

    private Integer idPedido;
}
