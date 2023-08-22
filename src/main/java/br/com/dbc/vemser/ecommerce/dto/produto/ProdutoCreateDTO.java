package br.com.dbc.vemser.ecommerce.dto.produto;

import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDTO {

    @NotBlank
    @Size(min = 1, max = 30, message = "Verifique a quantidade de caracteres.")
    @Schema(description = "Modelo do produto", required = true, example = "MANGALONGA")
    private String modelo;

    @NotNull
//    @Pattern(regexp = "[a-zA-Z]+", message = "Só é permitido um caractere: ex = P")
    @Schema(description = "Tamanho produto", type = "string", allowableValues = {"P", "M", "G"})
    private TipoTamanho tamanho;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "Somente letras é permitido.")
    @Size(min = 2, max = 150, message = "Verifique a quantidade de caracteres.")
    @Schema(description = "Cor do produto", required = true, example = "Branco")
    private String cor;

    @NotNull
    @Schema(description = "Setor do produto", type = "string", allowableValues = {"FEMININO", "MASCULINO", "INFANTIL"})
    private TipoSetor setor;

    @NotNull
    @Positive(message = "Não é permitido inserir valores menores do que zero.")
    @Schema(description = "Valor do produto", required = true)
    private Double valor;

    @Nullable
    @Schema(description = "URL da imagem", required = true, example = "https://www.imagemteste.com/imagemTeste")
    private String imgUrl;

    @Schema(description = "Descrição do produto",  example = "Camisa estampa star wars")
    private String descricao;


}